package com.example.simpleBbs.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import com.example.simpleBbs.dto.SimpleBbsDto;
import com.example.simpleBbs.entity.SimpleBbs;
import com.example.simpleBbs.repository.SimpleBbsRepository;

@Controller
public class SimpleBbsController {

	@Autowired
	private SimpleBbsRepository simpleBbsRepository;

	@GetMapping("/")
	public String main() {
		return "main";
	}

	@GetMapping("/simpleBbsForm")
	public String simpleBbsForm() {
		return "simpleBbsForm";
	}

	@PostMapping("/simpleBbsInsert")
	public String simpleBbsInsert(SimpleBbsDto dto) {

		SimpleBbs simpleBbs = SimpleBbs.toEntity(dto);

		simpleBbsRepository.save(simpleBbs);

		return "redirect:/simpleBbsList";
	}

	@GetMapping("/simpleBbsList")
	public String simpleBbsList(Model model, @RequestParam(name = "page", defaultValue = "1") int page) {
		int pageSize = 3;
		Pageable pageable = PageRequest.of(page - 1, pageSize);
		Page<SimpleBbs> simpleBbsPage = simpleBbsRepository.findAll(pageable);

		model.addAttribute("simpleBbsList", simpleBbsPage.getContent());
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", simpleBbsPage.getTotalPages());
		model.addAttribute("pageNumbers",
				IntStream.range(1, simpleBbsPage.getTotalPages() + 1).boxed().collect(Collectors.toList()));

		return "simpleBbsList";
	}

	@GetMapping("/simpleBbsGetId/{id}")
	public String simpleBbsGetId(@PathVariable("id") Long id, Model model) {

		Optional<SimpleBbs> simpleBbs = simpleBbsRepository.findById(id);

		if (simpleBbs.isPresent()) {

			model.addAttribute("simpleBbs", simpleBbs.get());

			return "simpleBbsDetail";
			
		} else {

			return "redirect:/simpleBbsList";
		}
	}

	@GetMapping("/simpleBbsList/{id}/delete")
	public String delete(@PathVariable("id") Long id) {

		SimpleBbs target = simpleBbsRepository.findById(id).orElse(null);

		if (target != null) {
			simpleBbsRepository.delete(target);
		}
		return "redirect:/simpleBbsList";
	}

	@GetMapping("/simpleBbsList/{id}/simpleBbsEdit")
	public String editSimpleBbsForm(@PathVariable("id") Long id, Model model) {
	    return simpleBbsRepository.findById(id)
	        .map(bbs -> {
	            model.addAttribute("simpleBbsDto", new SimpleBbsDto(bbs));
	            return "simpleBbsEditForm";
	        })
	        .orElse("redirect:/simpleBbsList");
	}

	@PostMapping("/update/{id}")
	public String updateBbs(@PathVariable("id") Long id, @ModelAttribute SimpleBbsDto dto) {
	    return simpleBbsRepository.findById(id)
	        .map(bbs -> {
	            bbs.setTitle(dto.getTitle());
	            bbs.setContent(dto.getContent());
	            bbs.setWriter(dto.getWriter());
	            simpleBbsRepository.save(bbs);
	            return "redirect:/simpleBbsDetail/" + id;
	        })
	        .orElse("redirect:/simpleBbsList");
	}

}
