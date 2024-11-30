package com.test.bidon.controller;

import com.test.bidon.entity.Faq;
import com.test.bidon.service.FaqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class FaqController {

    @Autowired
    private FaqService faqService;

    // FAQ 전체 목록 반환
    @GetMapping("/faqs")
    public List<Faq> getAllFaqs() {
        return faqService.getAllFaqs();
    }

    // FAQ 수정
    @PostMapping("/update-faq")
    public List<Faq> updateFaq(@RequestBody Faq updatedFaq) {
        faqService.updateFaq(updatedFaq.getId(), updatedFaq.getTitle(), updatedFaq.getContent());
        return faqService.getAllFaqs(); // 업데이트된 FAQ 목록 반환
    }
}
