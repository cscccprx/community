package life.majiang.community.service;

import life.majiang.community.dto.QuestionDTO;
import life.majiang.community.exception.CustomizeErrorCodeI;
import life.majiang.community.exception.CustomizeException;
import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.Question;
import life.majiang.community.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    QuestionMapper questionMapper;

    public List<QuestionDTO> list() {
        List<Question> questions = questionMapper.descOrderGetList();
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        //```([\\s\\S]*?)```[\\s]?
        return questionDTOList;
    }

    public List<Question> getListByid(Long id){
        List<Question> questions = questionMapper.getListById(id);
        return questions;
    }

    public QuestionDTO getQuestionById(Long id) {
        Question question = questionMapper.getQuestionById(id);
        if (question == null){
            throw new CustomizeException(CustomizeErrorCodeI.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user = userMapper.findById(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if (question.getId()==null){
            //创建
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.create(question);
        }else {
            question.setGmtModified(System.currentTimeMillis());
            questionMapper.update(question);
        }
    }

    public void incView(Long id) {
        questionMapper.updateView(id);
    }


    public List<QuestionDTO> selectRelated(QuestionDTO questionDTO) {
        if (StringUtils.isBlank(questionDTO.getTag())){
            return new ArrayList<>();
        }
        String[] split = questionDTO.getTag().split(",");
        String collect = Arrays.stream(split).collect(Collectors.joining("|"));
        List<QuestionDTO> questionDTOList = questionMapper.selectRelated(collect, questionDTO.getId());
        return questionDTOList;
    }

    public List<Question> getListById(Long id) {
        return questionMapper.getListById(id);
    }

    public List<QuestionDTO> getRelatedQuestionList(String search) {
        List<Question> relatedQuestionList = questionMapper.getRelatedQuestionList(search);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : relatedQuestionList) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        return questionDTOList;
    }

    public List<QuestionDTO> getQuestion(String tag, String search) {
        if (StringUtils.isNotBlank(tag)){
            return getRelatedQuestionList(tag);
        }else if (StringUtils.isNotBlank(search)){
            String[] split = search.split(" ");
            String collect = Arrays.stream(split).collect(Collectors.joining("|"));
            return getRelatedQuestionList(search);
        }else
            return list();


    }
}
