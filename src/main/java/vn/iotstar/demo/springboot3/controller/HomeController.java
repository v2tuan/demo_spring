package vn.iotstar.demo.springboot3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.iotstar.demo.springboot3.entity.Category;
import vn.iotstar.demo.springboot3.service.imp.CategoryService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/")
public class HomeController {
    @Autowired
    CategoryService categoryService;

    @GetMapping
    public String index(ModelMap model){
        int count = (int) categoryService.count();
        int currentPage = 1;
        int pageSize = 10;
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("categoryname"));

        Page<Category> resultPage = null;
        resultPage = categoryService.findAll(pageable);

        int totalPages = resultPage.getTotalPages();
        if (totalPages > 0) {
            int start = Math.max(1, currentPage - 2);
            int end = Math.min(currentPage + 2, totalPages);

            if (totalPages > count) {
                if (end == totalPages) start = end - count;
                else if (start == 1) end = start + count;
            }

            List<Integer> pageNumbers = IntStream.rangeClosed(start, end)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        model.addAttribute("categoryPage", resultPage);
        return "CRUD/list_main";
    }
}
