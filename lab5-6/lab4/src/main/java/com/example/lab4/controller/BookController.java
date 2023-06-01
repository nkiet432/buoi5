package com.example.lab4.controller;

import com.example.lab4.entity.Book;
import com.example.lab4.entity.Category;
import com.example.lab4.services.BookService;
import com.example.lab4.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@Controller
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String showAllBooks(Model model){
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books",books);
        return "book/list";
    }
    @GetMapping("/add")
    public String addBookForm(Model model){
        model.addAttribute("book",new Book());
        model.addAttribute("categories",categoryService.getAllCategories());
        return "book/add";
    }
    @PostMapping("/add")
    public  String addBook(@Valid@ModelAttribute("book") Book book, BindingResult bindingResult, Model model){
        if(bindingResult != null && bindingResult.hasErrors()){
            List<String> errors = bindingResult.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .toList();
            model.addAttribute("errors",errors);
            return "book/add";
        }
        bookService.addBook(book);
        return "redirect:/books";
    }

    @GetMapping({"/edit/{id}"})
    public String editBookForm(@PathVariable("id") Long id, Model model) {
        Book book = this.bookService.getBookById(id);
        model.addAttribute("book", book);
        model.addAttribute("categories", this.categoryService.getAllCategories());
        return "book/edit";
    }

    @PostMapping({"/edit/{id}"})
    public String editBook(@PathVariable("id") Long id, @ModelAttribute("book") Book book) {
        book.setId(id);
        this.bookService.updateBook(book);
        return "redirect:/books";
    }
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable ("id") Long id){
        bookService.deteleBook(id);
        return "redirect:/books";
    }

}
