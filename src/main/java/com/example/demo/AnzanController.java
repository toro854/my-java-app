package com.example.demo;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AnzanController {

    private final FlashAnzanLogic logic;

    public AnzanController(FlashAnzanLogic logic) {
        this.logic = logic;
    }

    @GetMapping("/")
    public String index(Model model) {

        model.addAttribute("gameStarted", logic.isGameStarted());
        model.addAttribute("level", logic.getLevel());
        model.addAttribute("score", logic.getScore());

        if (logic.isGameStarted()) {
            model.addAttribute("numbers", logic.generateQuestions());
            model.addAttribute("answer", logic.getTotalSum());
            model.addAttribute("choices", logic.getChoices());
        }

        return "index";
    }
}
    @GetMapping("/start")
    public String start(
        @RequestParam(defaultValue = "normal") String mode,
        @RequestParam(defaultValue = "1") int startLevel) { // レベルを受け取る
        
        logic.startGame(mode, startLevel);
        return "redirect:/";
    }

    @GetMapping("/reset")
    public String reset() {
        logic.reset();
        return "redirect:/";
    }

    @GetMapping("/next")
    public String next(@RequestParam(defaultValue = "0") double bonus) {
        logic.addScore(bonus); // スコア加算
        logic.incrementLevel();
        return "redirect:/";
    }
}