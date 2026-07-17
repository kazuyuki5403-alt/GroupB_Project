package com.example.demo.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dao.FormContactDAO;
import com.example.demo.dao.MenuDAO;
import com.example.demo.model.FormContact;
import com.example.demo.model.Member;
import com.example.demo.model.MenuList;

//@Controller
public class FormController {

    /*
     * お問い合わせ内容を保存・取得するDAO
     */
    @Autowired
    private FormContactDAO formContactDAO;

    /*
     * 予約内容を保存・取得するDAO
     */
    @Autowired
    private MenuDAO menuDAO;


    /**
     * お問い合わせ画面を表示する
     *
     * URL：
     * GET /form
     *
     * 遷移先：
     * form.jsp
     */
    @GetMapping("/form")
    public String showForm() {

        return "form";
    }


    /**
     * メニュー・予約画面を表示する
     *
     * URL：
     * GET /menu
     *
     * 遷移先：
     * menu.jsp
     */
    @GetMapping("/menu")
    public String showMenu() {

        return "menu";
    }


    /**
     * form.jspまたはmenu.jspから送信された内容を受け取る
     *
     * genreの値によって、
     * お問い合わせと予約を分ける。
     *
     * genre：
     * contact     → お問い合わせ
     * reservation → 予約
     */
    @PostMapping("/form/submit")
    public String submitForm(

            // 共通項目
            @RequestParam(name = "genre") String genre,

            @RequestParam(
                    name = "customerName",
                    required = false)
            String customerName,

            @RequestParam(
                    name = "email",
                    required = false)
            String email,

            @RequestParam(
                    name = "phone",
                    required = false)
            String phone,

            // お問い合わせ用
            @RequestParam(
                    name = "subject",
                    required = false)
            String subject,

            @RequestParam(
                    name = "message",
                    required = false)
            String message,

            // 予約用
            @RequestParam(
                    name = "menuId",
                    required = false)
            Integer menuId,

            @RequestParam(
                    name = "reservationDate",
                    required = false)
            String reservationDate,

            @RequestParam(
                    name = "reservationTime",
                    required = false)
            String reservationTime,

            @RequestParam(
                    name = "numberOfPeople",
                    required = false)
            Integer numberOfPeople,

            Model model) {

        /*
         * 共通項目の入力チェック
         */
        if (customerName == null
                || customerName.isBlank()) {

            model.addAttribute(
                    "errorMsg",
                    "お名前を入力してください");

            model.addAttribute("genre", genre);
            model.addAttribute(
                    "customerName",
                    customerName);
            model.addAttribute("email", email);
            model.addAttribute("phone", phone);

            return returnInputPage(genre);
        }


        if (email == null
                || email.isBlank()) {

            model.addAttribute(
                    "errorMsg",
                    "メールアドレスを入力してください");

            model.addAttribute("genre", genre);
            model.addAttribute(
                    "customerName",
                    customerName);
            model.addAttribute("phone", phone);

            return returnInputPage(genre);
        }


        /*
         * お問い合わせの場合
         */
        if ("contact".equals(genre)) {

            return saveContact(
                    customerName,
                    email,
                    phone,
                    subject,
                    message,
                    model);
        }


        /*
         * 予約の場合
         */
        if ("reservation".equals(genre)) {

            return saveReservation(
                    customerName,
                    email,
                    phone,
                    menuId,
                    reservationDate,
                    reservationTime,
                    numberOfPeople,
                    model);
        }


        /*
         * genreが不正な場合
         */
        model.addAttribute(
                "errorMsg",
                "送信内容の種類が正しくありません");

        return "form";
    }


    /**
     * お問い合わせ内容を保存する
     */
    private String saveContact(
            String customerName,
            String email,
            String phone,
            String subject,
            String message,
            Model model) {

        /*
         * お問い合わせ内容の入力チェック
         */
        if (subject == null
                || subject.isBlank()) {

            model.addAttribute(
                    "errorMsg",
                    "お問い合わせ件名を入力してください");

            model.addAttribute(
                    "customerName",
                    customerName);

            model.addAttribute(
                    "email",
                    email);

            model.addAttribute(
                    "phone",
                    phone);

            model.addAttribute(
                    "message",
                    message);

            return "form";
        }


        if (message == null
                || message.isBlank()) {

            model.addAttribute(
                    "errorMsg",
                    "お問い合わせ内容を入力してください");

            model.addAttribute(
                    "customerName",
                    customerName);

            model.addAttribute(
                    "email",
                    email);

            model.addAttribute(
                    "phone",
                    phone);

            model.addAttribute(
                    "subject",
                    subject);

            return "form";
        }


        /*
         * Modelへお問い合わせ情報を格納
         */
        FormContact contact =
                new FormContact();

        contact.setCustomerName(customerName);
        contact.setEmail(email);
        contact.setPhone(phone);
        contact.setSubject(subject);
        contact.setMessage(message);


        /*
         * DAOへ保存を依頼
         */
        boolean result =
                formContactDAO.insert(contact);


        /*
         * DBへの保存失敗
         */
        if (!result) {

            model.addAttribute(
                    "errorMsg",
                    "お問い合わせの保存に失敗しました");

            return "form";
        }


        /*
         * Thankyou.jspに表示する情報
         */
        model.addAttribute(
                "genre",
                "お問い合わせ");

        model.addAttribute(
                "customerName",
                customerName);

        model.addAttribute(
                "completeMsg",
                "お問い合わせを受け付けました");

        return "Thankyou";
    }


    /**
     * 予約内容を保存する
     */
    private String saveReservation(
            String customerName,
            String email,
            String phone,
            Integer menuId,
            String reservationDate,
            String reservationTime,
            Integer numberOfPeople,
            Model model) {

        /*
         * メニュー未選択
         */
        if (menuId == null) {

            model.addAttribute(
                    "errorMsg",
                    "メニューを選択してください");

            return "menu";
        }


        /*
         * 予約日未入力
         */
        if (reservationDate == null
                || reservationDate.isBlank()) {

            model.addAttribute(
                    "errorMsg",
                    "予約日を入力してください");

            return "menu";
        }


        /*
         * 予約時間未入力
         */
        if (reservationTime == null
                || reservationTime.isBlank()) {

            model.addAttribute(
                    "errorMsg",
                    "予約時間を入力してください");

            return "menu";
        }


        /*
         * 人数未入力または0人以下
         */
        if (numberOfPeople == null
                || numberOfPeople <= 0) {

            model.addAttribute(
                    "errorMsg",
                    "予約人数を正しく入力してください");

            return "menu";
        }


        try {

            /*
             * Stringを日付・時刻へ変換
             */
            LocalDate date =
                    LocalDate.parse(
                            reservationDate);

            LocalTime time =
                    LocalTime.parse(
                            reservationTime);


            /*
             * 予約情報をModelへ格納
             */
            MenuList reservation =
                    new MenuList();

            reservation.setCustomerName(
                    customerName);

            reservation.setEmail(email);
            reservation.setPhone(phone);
            reservation.setMenuId(menuId);

            reservation.setReservationDate(
                    date);

            reservation.setReservationTime(
                    time);

            reservation.setNumberOfPeople(
                    numberOfPeople);


            /*
             * MenuDAOへ保存を依頼
             */
            boolean result =
                    menuDAO.insertReservation(
                            reservation);


            /*
             * DBへの保存失敗
             */
            if (!result) {

                model.addAttribute(
                        "errorMsg",
                        "予約情報の保存に失敗しました");

                return "menu";
            }


            /*
             * Thankyou.jspに表示する情報
             */
            model.addAttribute(
                    "genre",
                    "予約");

            model.addAttribute(
                    "customerName",
                    customerName);

            model.addAttribute(
                    "reservationDate",
                    date);

            model.addAttribute(
                    "reservationTime",
                    time);

            model.addAttribute(
                    "completeMsg",
                    "ご予約を受け付けました");

            return "Thankyou";

        } catch (Exception e) {

            model.addAttribute(
                    "errorMsg",
                    "予約日または予約時間の形式が正しくありません");

            return "menu";
        }
    }


    /**
     * 管理者画面を表示する
     *
     * お問い合わせ一覧と予約一覧を
     * DBから取得してadmin.jspへ渡す。
     */
    @GetMapping("/admin")
    public String showAdmin(
            HttpSession session,
            Model model) {

        /*
         * セッションからログイン会員を取得
         */
        Member loginMember =
                (Member) session.getAttribute(
                        "loginMember");


        /*
         * 未ログインの場合
         */
        if (loginMember == null) {

            model.addAttribute(
                    "errorMsg",
                    "ログインしてください");

            return "index";
        }


        /*
         * 管理者権限の確認
         */
        if (!"admin".equals(
                loginMember.getRole())) {

            model.addAttribute(
                    "errorMsg",
                    "管理者以外は閲覧できません");

            return "main";
        }


        /*
         * お問い合わせ一覧を取得
         */
        List<FormContact> contactList =
                formContactDAO.findAll();


        /*
         * 予約一覧を取得
         */
        List<MenuList> reservationList =
                menuDAO.findAllReservations();


        /*
         * admin.jspへ渡す
         */
        model.addAttribute(
                "contactList",
                contactList);

        model.addAttribute(
                "reservationList",
                reservationList);

        return "admin";
    }


    /**
     * エラー時に戻る画面を判断する
     */
    private String returnInputPage(
            String genre) {

        if ("reservation".equals(genre)) {

            return "menu";
        }

        return "form";
    }
}