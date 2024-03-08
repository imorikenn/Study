package com.example.demo.controller;

import java.util.List;

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
import com.example.demo.entity.Author;
import com.example.demo.entity.Book;
import com.example.demo.service.AuthorService;
import com.example.demo.service.BookService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/authors")
public class AuthorsController {
	@Autowired
	AuthorService authorService;

	@Autowired
	BookService bookService;

	@GetMapping
	public String list(Model model) {
		model.addAttribute("authors", authorService.findAll());
		return "authors/list";
	}
	
	@GetMapping("/create")
	public String add(@ModelAttribute Author author, Model model) {
		model.addAttribute("isNew", true);
		return "authors/form";
	}

	@PostMapping("/process")
	public String process(@Validated @ModelAttribute Author author, BindingResult result, Model model, RedirectAttributes ra) {
		FlashData flash;
		try {
			if (result.hasErrors()) {
				model.addAttribute("isNew", author.getId() == null);
				return "authors/form";
			}
			String type = (author.getId() == null) ? "追加" : "編集";
			authorService.save(author);
			flash = new FlashData().success("著者の" + type + "が完了しました");
		} catch (Exception e) {
			flash = new FlashData().danger("処理中にエラーが発生しました");
		}
		ra.addFlashAttribute("flash", flash);
		return "redirect:/authors";
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable Integer id, Model model) {
		model.addAttribute("isNew", false);
		try {
			model.addAttribute("author", authorService.findById(id));
		} catch (DataNotFoundException e) {
			return "redirect:/authors";
		}
		return "authors/form";
	}
	
	// 著者削除
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Integer id, RedirectAttributes ra) {
    		FlashData flash;
    		try {
        		List<Book> books = bookService.findByAuthorId(id);
        		if (books.isEmpty()) {
            			authorService.findById(id);
            			authorService.delete(id);
            			flash = new FlashData().success("著者の削除が完了しました");
        		} else {
            			flash = new FlashData().danger("書籍に登録されている著者は削除できません");
        		}
    		} catch (DataNotFoundException e) {
        		flash = new FlashData().danger("該当データがありません");
    		} catch (Exception e) {
        		flash = new FlashData().danger("処理中にエラーが発生しました");
    		}
    		ra.addFlashAttribute("flash", flash);
    		return "redirect:/authors";
	}

}
