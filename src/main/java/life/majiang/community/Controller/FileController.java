package life.majiang.community.Controller;

import life.majiang.community.dto.FileDTO;
import life.majiang.community.exception.CustomizeErrorCodeI;
import life.majiang.community.exception.CustomizeException;
import life.majiang.community.provider.UcloudProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

@Controller
public class FileController {

    @Autowired
    private UcloudProvider ucloudProvider;


    @RequestMapping("/file/upload")
    @ResponseBody
    public FileDTO upload(HttpServletRequest request)  {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
        MultipartFile file = ((MultipartHttpServletRequest) request).getFile("editormd-image-file");
        FileDTO fileDTO = new FileDTO();
        try {
            String fileName = ucloudProvider.upload(file.getInputStream(), file.getOriginalFilename());
            fileDTO = new FileDTO();
            fileDTO.setSuccess(1);
            fileDTO.setUrl(fileName);
            return fileDTO;
        } catch (Exception e) {
            throw new CustomizeException(CustomizeErrorCodeI.FILE_UPLOAD_FAIL);
        }

    }
}
