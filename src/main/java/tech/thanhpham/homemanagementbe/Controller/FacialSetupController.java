package tech.thanhpham.homemanagementbe.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tech.thanhpham.homemanagementbe.DTO.FacialSetupDTO;
import tech.thanhpham.homemanagementbe.DTO.imageVerifyRequest;
import tech.thanhpham.homemanagementbe.Entity.Video;
import tech.thanhpham.homemanagementbe.Service.imageVerifyService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

@Controller
public class FacialSetupController {
    @Autowired
    private imageVerifyService imageVerifyService;

    @GetMapping("/facial-setup")
    public String index(Model model, HttpServletRequest request, RedirectAttributes redirect) {
        request.getSession().setAttribute("facialSetupList", null);

        if (model.asMap().get("success") != null)
            redirect.addFlashAttribute("success", model.asMap().get("success").toString());
        return "redirect:/facial-setup/page/1";
    }

    @GetMapping("/facial-setup/page/{pageNumber}")
    public String showEmployeePage(HttpServletRequest request, @PathVariable int pageNumber, Model model) {
        model.addAttribute("request", new imageVerifyRequest());
        PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("facialSetupList");
        int pagesize = 10;
        List<FacialSetupDTO> list =  imageVerifyService.getFacialSetupDTOList();
//        list.forEach(facialSetupDTO -> System.out.println(facialSetupDTO.getImages()));
        if (pages == null) {
            pages = new PagedListHolder<>(list);
            pages.setPageSize(pagesize);
        } else {
            final int goToPage = pageNumber - 1;
            if (goToPage <= pages.getPageCount() && goToPage >= 0) {
                pages.setPage(goToPage);
            }
        }
        request.getSession().setAttribute("facialSetupList", pages);
        int current = pages.getPage() + 1;
        int begin = Math.max(1, current - list.size());
        int end = Math.min(begin + 5, pages.getPageCount());
        int totalPageCount = pages.getPageCount();
        String baseUrl = "/facial-setup/page/";

        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current);
        model.addAttribute("totalPageCount", totalPageCount);
        model.addAttribute("baseUrl", baseUrl);
        model.addAttribute("faces", pages);

        return "facialsetup";
    }


    @GetMapping("/facial-setup/delete")
    public String delete(@RequestParam String ids, RedirectAttributes redirect) {
        imageVerifyService.deleteFacialSetup("[" + ids + "]");
        redirect.addFlashAttribute("success", "Deleted video successfully!");
        return "redirect:/facial-setup";
    }

    @PostMapping("/facial-setup/setup")
    public String setup(@ModelAttribute imageVerifyRequest request, Model model) {
        model.addAttribute("request", request);
        request.setData(request.getData().replace("data:image/png;base64,", ""));
        imageVerifyService.imageVerify(request);
        return "redirect:/facial-setup";
    }

//    @GetMapping("/videos/search/{pageNumber}")
//    public String search(@RequestParam("s") String s, Model model, HttpServletRequest request,
//                         @PathVariable int pageNumber) {
//        if (s.equals("")) {
//            return "redirect:/videos";
//        }
//        List<Video> list = videoRepository.findByVideoName(s);
//        if (list == null) {
//            return "redirect:/videos";
//        }
//        PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("employeelist");
//        int pagesize = 10;
//        pages = new PagedListHolder<>(list);
//        pages.setPageSize(pagesize);
//
//        final int goToPage = pageNumber - 1;
//        if (goToPage <= pages.getPageCount() && goToPage >= 0) {
//            pages.setPage(goToPage);
//        }
//        request.getSession().setAttribute("videolist", pages);
//        int current = pages.getPage() + 1;
//        int begin = Math.max(1, current - list.size());
//        int end = Math.min(begin + 5, pages.getPageCount());
//        int totalPageCount = pages.getPageCount();
//        String baseUrl = "/videos/page/";
//        model.addAttribute("beginIndex", begin);
//        model.addAttribute("endIndex", end);
//        model.addAttribute("currentIndex", current);
//        model.addAttribute("totalPageCount", totalPageCount);
//        model.addAttribute("baseUrl", baseUrl);
//        model.addAttribute("employees", pages);
//        return "list";
//    }
}
