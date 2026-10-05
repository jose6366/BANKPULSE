package com.usfq.bankpulse.controller;
import com.usfq.bankpulse.dto.CreatePaymentRequest;
import com.usfq.bankpulse.service.PaymentService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.math.BigDecimal;
@Controller
public class WebController {
  private final PaymentService service;
  public WebController(PaymentService service){ this.service=service; }
  @GetMapping("/") public String home(Model model){ model.addAttribute("accounts",service.accounts()); model.addAttribute("payments",service.payments()); return "index"; }
  @GetMapping("/payments/new") public String form(Model model){ model.addAttribute("accounts",service.accounts()); return "payment-form"; }
  @PostMapping("/payments") public String create(@RequestParam Long sourceAccountId,@RequestParam String beneficiary,@RequestParam BigDecimal amount,Authentication auth,RedirectAttributes ra){
    try { var p=service.create(new CreatePaymentRequest(sourceAccountId,beneficiary,amount),auth.getName()); ra.addFlashAttribute("success","Pago "+p.getReference()+" creado correctamente"); }
    catch(Exception e){ ra.addFlashAttribute("error",e.getMessage()); }
    return "redirect:/";
  }
}
