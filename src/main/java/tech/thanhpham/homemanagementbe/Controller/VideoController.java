package tech.thanhpham.homemanagementbe.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tech.thanhpham.homemanagementbe.Entity.Video;
import tech.thanhpham.homemanagementbe.Repository.VideoRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

@Controller
public class VideoController {
    @Autowired
    private VideoRepository videoRepository;

    @GetMapping("/videos")
    public String index(Model model,HttpServletRequest request
            ,RedirectAttributes redirect) {
        request.getSession().setAttribute("videolist", null);

        if(model.asMap().get("success") != null)
            redirect.addFlashAttribute("success",model.asMap().get("success").toString());
        return "redirect:/videos/page/1";
    }

    @GetMapping("/videos/page/{pageNumber}")
    public String showEmployeePage(HttpServletRequest request,
                                   @PathVariable int pageNumber, Model model) {
        PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("videolist");
        int pagesize = 10;
        List<Video> list =(List<Video>) videoRepository.findAllByOrderByCreationDateDesc();
        System.out.println(list.size());
        if (pages == null) {
            pages = new PagedListHolder<>(list);
            pages.setPageSize(pagesize);
        } else {
            final int goToPage = pageNumber - 1;
            if (goToPage <= pages.getPageCount() && goToPage >= 0) {
                pages.setPage(goToPage);
            }
        }
        request.getSession().setAttribute("videolist", pages);
        int current = pages.getPage() + 1;
        int begin = Math.max(1, current - list.size());
        int end = Math.min(begin + 5, pages.getPageCount());
        int totalPageCount = pages.getPageCount();
        String baseUrl = "/videos/page/";

        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current);
        model.addAttribute("totalPageCount", totalPageCount);
        model.addAttribute("baseUrl", baseUrl);
        model.addAttribute("videos", pages);

        return "list";
    }


    @GetMapping("/videos/{id}/delete")
    public String delete(@PathVariable UUID id, RedirectAttributes redirect) {
        Video emp = videoRepository.findById(id).get();
        videoRepository.delete(emp);
//        redirect.addFlashAttribute("success", "Deleted video successfully!");
        return "redirect:/videos";
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
