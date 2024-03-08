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
import com.example.demo.entity.Book;
import com.example.demo.service.BookService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/books")
public class BooksController {
	@Autowired
	BookService bookService;
	
	@GetMapping
	public String list(Model model) {
		model.addAttribute("books", bookService.findAll());
		return "books/list";
	}
	
	@GetMapping("/create")
	public String create(@ModelAttribute Book book, Model model) {
		model.addAttribute("isNew", true);
		return "books/form";
	}

	@PostMapping("/process")
	public String process(@Validated @ModelAttribute Book book, BindingResult result, Model model, RedirectAttributes ra) {
		FlashData flash;
		try {
			if (result.hasErrors()) {
				model.addAttribute("isNew", book.getId() == null);
				return "books/form";
			}
			String type = (book.getId() == null) ? "追加" : "編集";
			bookService.save(book);
			flash = new FlashData().success("書籍の" + type + "が完了しました");
		} catch (Exception e) {
			flash = new FlashData().danger("処理中にエラーが発生しました");
		}
		ra.addFlashAttribute("flash", flash);
		return "redirect:/books";
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable Integer id, Model model) {
		model.addAttribute("isNew", false);
		try {
			model.addAttribute("book", bookService.findById(id));
		} catch (DataNotFoundException e) {
			return "redirect:/books";
		}
		return "books/form";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Integer id, RedirectAttributes ra) {
		FlashData flash;
		try {
			bookService.findById(id);
			bookService.delete(id);
			flash = new FlashData().success("書籍の削除が完了しました");
			ra.addFlashAttribute("flash", flash);
		} catch (DataNotFoundException e) {
			flash = new FlashData().danger("該当データがありません");
		} catch (Exception e) {
			flash = new FlashData().danger("処理中にエラーが発生しました");
		}
		ra.addFlashAttribute("flash", flash);
		return "redirect:/books";
	}


}
