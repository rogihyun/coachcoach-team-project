package com.coachcoach.web.myCoach;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/myCoach/pay")
public class PayController {
	
	

  @GetMapping("list") // 결제내역
  public void list() {}

}
