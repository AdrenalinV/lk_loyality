package ru.gb.lk_loyality.controllers;


import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import ru.gb.lk_loyality.dto.*;
import ru.gb.lk_loyality.entities.User;
import ru.gb.lk_loyality.exceptions.ResourceNotFoundException;
import ru.gb.lk_loyality.security.UserInfo;
import ru.gb.lk_loyality.services.CardService;
import ru.gb.lk_loyality.services.CounterService;
import ru.gb.lk_loyality.services.DocumentService;
import ru.gb.lk_loyality.services.UserService;


import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/front")
@RequiredArgsConstructor
public class WebController {
    private final UserService userService;
    private final CardService cardService;
    private final CounterService counterService;
    private final DocumentService documentService;

    @GetMapping({"/", "/index"})
    public String showIndex(Model model, HttpSession session) {
        UserInfo userInfo;
        try {
            userInfo = (UserInfo) session.getAttribute("userInfo");
        } catch (Exception e) {
            return "redirect:/front/login";
        }
        if (userInfo == null) {
            return "redirect:/front/login";
        }
        System.out.println("[DEBUG] " + userInfo);
        UserDto userDto = userService.getUserDtoById(userInfo.getId())
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Пользователь с id = %d не найден", userInfo.getId())));
        CardDto cardDto = cardService.getCardDtoByNumber(userDto.getCardNumber())
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Карта с номером %d не найдена", userDto.getCardNumber())));
        var activeBonus = cardService.updateActiveBonusByCardNumber(userDto.getCardNumber());
        var nonActiveBonus = cardService.getNoActiveBonusByCardNumber(userDto.getCardNumber());
        var sumDocuments = documentService.getSumByCardId(userInfo.getCard_id());
        List<CounterDto> listCounters = counterService.getListCountersByPeriod(
                userInfo.getCard_id(),
                LocalDate.now().withDayOfMonth(1),
                LocalDate.now());
        model.addAttribute("currentDate", LocalDate.now());
        model.addAttribute("userInfo", userInfo);
        model.addAttribute("userDto", userDto);
        model.addAttribute("cardDto", cardDto);
        model.addAttribute("activeBonus", String.format("%.2f", activeBonus));
        model.addAttribute("nonBonus", String.format("%.2f", nonActiveBonus));
        model.addAttribute("sumDocuments", String.format("%.2f", sumDocuments));
        model.addAttribute("counters", listCounters);
        return "index";
    }

    @GetMapping("/login")
    public String showLogin(Model model) {
        AuthRequestDto authRequestDto = new AuthRequestDto();
        model.addAttribute(authRequestDto);
        return "login";
    }

    @PostMapping("/login")
    public String getLogin(Model model, @ModelAttribute("authRequestDto") AuthRequestDto request, HttpSession session) {
        User user;
        try {
            user = userService.findByNameAndPassword(request.getUserName(), request.getPassword());
        } catch (Exception e) {
            String msg = "Имя пользователя и/или пароль неверен.";
            model.addAttribute("msg", msg);
            return "login";
        }
        List<String> roles = new ArrayList<>();
        user.getRoles().forEach(role -> roles.add(role.getName()));
        UserInfo userInfo = UserInfo.builder()
                .userName(user.getName())
                .id(user.getId())
                .card_id(user.getCard().getId())
                .roles(roles)
                .build();
        session.setAttribute("userInfo", userInfo);
        return "redirect:/front/index";
    }

    @GetMapping("/bonus")
    public String showBonus(Model model,
                            HttpSession session) {
        LocalDate begin;
        LocalDate end;
        UserInfo userInfo;
        try {
            userInfo = (UserInfo) session.getAttribute("userInfo");
        } catch (Exception e) {
            return "redirect:/front/login";
        }
        if (userInfo == null) {
            return "redirect:/front/login";
        }
        begin = LocalDate.now().withDayOfMonth(1);
        end = LocalDate.now();
        List<CounterDto> listCounters = counterService.getListCountersByPeriod(userInfo.getCard_id(), begin, end);

        model.addAttribute("currentDate", LocalDate.now());
        model.addAttribute("userInfo", userInfo);
        model.addAttribute("begin", begin);
        model.addAttribute("end", end);
        model.addAttribute("counters", listCounters);
        return "bonusdetails";
    }

    @PostMapping("/bonus")
    public String showBonus(Model model,
                            @ModelAttribute("begin") String rowBegin,
                            @ModelAttribute("end") String rowEnd,
                            HttpSession session) {
        UserInfo userInfo;
        try {
            userInfo = (UserInfo) session.getAttribute("userInfo");
        } catch (Exception e) {
            return "redirect:/front/login";
        }
        if (userInfo == null) {
            return "redirect:/front/login";
        }
//        List<CounterDto> listCounters = counterService.getListCountersByPeriod(
//                userInfo.getCard_id(),
//                rowBegin,
//                rowEnd
//        );
//        System.out.println("[DEBUG] listCounters: " + listCounters);
//        SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd.MM.yyyy");
//        model.addAttribute("begin", formatForDateNow.format(begin));
//        model.addAttribute("end", formatForDateNow.format(end));
//        model.addAttribute("counters", listCounters);
        return "bonusdetails";
    }

    @GetMapping("/documents")
    public String showDocuments(Model model,
                                HttpSession session) {
        LocalDate begin;
        LocalDate end;
        UserInfo userInfo;
        try {
            userInfo = (UserInfo) session.getAttribute("userInfo");
        } catch (Exception e) {
            return "redirect:/front/login";
        }
        if (userInfo == null) {
            return "redirect:/front/login";
        }
        begin = LocalDate.now().withDayOfMonth(1);
        end = LocalDate.now();
        List<DocumentDto> documentsDto = documentService.getDocumentsDtoAmount(userInfo.getCard_id(), begin, end);

        model.addAttribute("currentDate", LocalDate.now());
        model.addAttribute("userInfo", userInfo);
        model.addAttribute("begin", begin);
        model.addAttribute("end", end);
        model.addAttribute("documents", documentsDto);
        return "documents";
    }

    @GetMapping("/register")
    public String showRegister(Model model) {
        SignUpRequestDto request = new SignUpRequestDto();
        model.addAttribute("signUpRequest", request);
        return "register";
    }

    @PostMapping("/register")
    public String setRegister(Model model, @ModelAttribute("signUpRequest") SignUpRequestDto request) {
        if (request.getPassword().equals(request.getPasPassword())) {
            User user = new User();
            user.setPassword(request.getPassword());
            user.setName(request.getUserName());
            user.setEmail(request.getEmail());
            userService.saveUser(user);
            return "redirect:/front/login";
        }
        String msg = "не совпадает пароль.";
        model.addAttribute("msg", msg);
        return "register";
    }
}
