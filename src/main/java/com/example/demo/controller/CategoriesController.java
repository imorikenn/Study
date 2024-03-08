package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.common.DataNotFoundException;
import com.example.demo.common.FlashData;
import com.example.demo.entity.Category;
import com.example.demo.service.BookService;
import com.example.demo.service.CategoryService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/categories")
public class CategoriesController {
	@Autowired
	CategoryService categoryService;

	@Autowired
	BookService bookService;

	@GetMapping
	public String list(Model model) {
		model.addAttribute("categories", categoryService.findAll());
		return "categories/list";
	}
	
	@GetMapping("/create")
	public String add(@ModelAttribute Category category, Model model) {
		model.addAttribute("isNew", true);
		return "categories/form";
	}

	@PostMapping("/process")
	public String process(@Validated @ModelAttribute Category category, BindingResult result, Model model, RedirectAttributes ra) {
		FlashData flash;
		try {
			if (result.hasErrors()) {
				model.addAttribute("isNew", category.getId() == null);
				return "categories/form";
			}
			String type = (category.getId() == null) ? "追加" : "編集";
			categoryService.save(category);
			flash = new FlashData().success("カテゴリの" + type + "が完了しました");
		} catch (Exception e) {
			flash = new FlashData().danger("処理中にエラーが発生しました");
		}
		ra.addFlashAttribute("flash", flash);
		return "redirect:/categories";
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable Integer id, Model model) {
		model.addAttribute("isNew", false);
		try {
			model.addAttribute("category", categoryService.findById(id));
		} catch (DataNotFoundException e) {
			return "redirect:/categories";
		}
		return "categories/form";
	}

}
