package vn.iotstar.demo.springboot3.controller;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vn.iotstar.demo.springboot3.entity.Category;
import vn.iotstar.demo.springboot3.service.imp.CategoryService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/categories")
public class CategoryController {

        @Autowired
        CategoryService categoryService;
        @GetMapping
        public String getAll(ModelMap model){
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

        @GetMapping("/signup")
        public String signup(Category category, ModelMap modelMap){
                modelMap.addAttribute("category", category);
                return "CRUD/category_add";
        }

        @PostMapping("/add")
        public String add(@Valid Category category, BindingResult result, Model model){
                if (result.hasErrors()) {
                        return "CRUD/category_add";
                }
                categoryService.save(category);
                return "redirect:/categories";
        }

        @GetMapping("/delete/{id}")
        public String delete(@PathVariable("id") long id, Model model){
                categoryService.deleteById(id);
                return "redirect:/categories";
        }

        @GetMapping("/edit/{id}")
        public String edit(@PathVariable("id") long id, Model model){
                Category category = categoryService.findById(id)
                        .orElseThrow(() -> new RuntimeException("Not found"));
                model.addAttribute("category", category);
                return "CRUD/category_update";
        }

        @PostMapping("/update/{id}")
        public String update(@PathVariable("id") long id, @Valid Category category, BindingResult result,
                             Model model){
                if (result.hasErrors()){
                        return "CRUD/category_update";
                }
                categoryService.save(category);
                return "redirect:/categories";
        }

        @RequestMapping("/searchpaginated")
        public String search(ModelMap model,
                             @RequestParam(name = "name", required = false) String categoryname,
                             @RequestParam("page") Optional<Integer> page,
                             @RequestParam("size") Optional<Integer> size) {
                int count = (int) categoryService.count();
                int currentPage = page.orElse(1);
                int pageSize = size.orElse(3);
                Pageable pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("categoryname"));

                Page<Category> resultPage = null;
                if (StringUtils.hasText(categoryname)) {
                        resultPage = categoryService.findByCategorynameContaining(categoryname, pageable);
                        model.addAttribute("categoryname", categoryname);
                } else {
                        resultPage = categoryService.findAll(pageable);
                }

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
