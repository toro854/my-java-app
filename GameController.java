package com.example.demo;

import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class GameController {

    private final UserRepository userRepository;
    private final ScoreRepository scoreRepository;
    private final PasswordEncoder passwordEncoder;

    public GameController(UserRepository userRepository, ScoreRepository scoreRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.scoreRepository = scoreRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/user-login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session, RedirectAttributes redirectAttributes) {
        
        User user = userRepository.findByUsername(username);
        
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            // ログイン成功
            session.setAttribute("loggedInUser", username);
        } else {
            // パスワードやユーザー名が違う場合のエラーメッセージ
            redirectAttributes.addFlashAttribute("errorMessage", "エラー：ユーザー名またはパスワードが間違っています。");
        }
        return "redirect:/";
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // セッション（ログイン状態）を破棄してログアウトさせる
        session.invalidate(); 
        
        // トップ画面に戻す
        return "redirect:/";
    }
    
    @GetMapping("/")
    public String index(HttpSession session, Model model) {
        // セッション（現在のログイン状態）からユーザー名を取得
        String loggedInUser = (String) session.getAttribute("loggedInUser");
        
        // 取得したユーザー名を "loggedInUser" という名前でHTMLに渡す
        model.addAttribute("loggedInUser", loggedInUser);
        
        return "index"; // index.html を表示する
    }
    
    
    // --- ログイン・登録処理 ---
    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password, HttpSession session, RedirectAttributes redirectAttributes) {
        
        // --- 1. 入力値のチェック（バリデーション） ---
        
        // ユーザー名が未入力、またはスペースのみの場合（trim()で前後のスペースを消して判定）
        if (username == null || username.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "エラー：ユーザー名を入力してください（スペースのみは不可）。");
            return "redirect:/"; // トップ画面に戻す
        }
        
        // パスワードが未入力、またはスペースのみの場合
        if (password == null || password.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "エラー：パスワードを入力してください。");
            return "redirect:/";
        }
        
        // パスワードの文字数チェック（4文字未満、または10文字より大きい場合）
        if (password.length() < 4 || password.length() > 10) {
            redirectAttributes.addFlashAttribute("errorMessage", "エラー：パスワードは4文字以上、10文字以下で入力してください。");
            return "redirect:/";
        }

        // --- 2. データベースへの保存処理（既存コード） ---
        
        // 前後の余白（スペース）を取り除いた綺麗なユーザー名にする
        String cleanUsername = username.trim();

        if (userRepository.findByUsername(cleanUsername) == null) {
            User user = new User();
            user.setUsername(cleanUsername); // 綺麗なユーザー名を保存
            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user);
            
            session.setAttribute("loggedInUser", cleanUsername);
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "エラー：そのユーザー名はすでに使われています。");
        }
        return "redirect:/";
    }

    // --- スコア保存処理（ゲスト判定） ---
    @PostMapping("/api/saveScore")
    @ResponseBody
    public String saveScore(@RequestParam int score, HttpSession session) {
        String username = (String) session.getAttribute("loggedInUser");
        
        // ゲスト（未ログイン）の場合は保存しない
        if (username == null) {
            return "guest";
        }

        // ログインしている場合は保存する
        ScoreRecord record = new ScoreRecord();
        record.setUsername(username);
        record.setScore(score);
        scoreRepository.save(record);
        return "success";
    }
    
    // ランキング表示
    @GetMapping("/ranking")
    public String ranking(Model model) {
        // スコアが高い順に上位10件を取得して画面に渡す
        List<ScoreRecord> topScores = scoreRepository.findTop10ByOrderByScoreDesc();
        model.addAttribute("ranking", topScores);
        return "ranking";
    }
}