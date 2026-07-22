package com.example.demo.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.model.MenuList;

@Repository
public class MenuDAO {

    /*
     * 予約データを一時的に保存するList
     *
     * Spring Bootを再起動すると、
     * 保存したデータは消える。
     */
    private final List<MenuList> reservationList =
            new ArrayList<>();


    /**
     * 予約データを保存する
     *
     * @param reservation 予約情報
     * @return 保存成功：true
     *         保存失敗：false
     */
    public boolean insertReservation(
            MenuList reservation) {

        try {

            reservationList.add(
                    reservation);

            return true;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }


    /**
     * 予約データをすべて取得する
     *
     * @return 予約一覧
     */
    public List<MenuList> findAllReservations() {

        /*
         * DAO内部のListを直接渡さず、
         * 新しいListへコピーして返す。
         */
        return new ArrayList<>(
                reservationList);
    }
}