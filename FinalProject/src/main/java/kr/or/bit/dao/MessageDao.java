package kr.or.bit.dao;

import kr.or.bit.model.Message;

/*
*
* @date: 2019. 06. 21.
*
* @author: 정성윤
*
* @description: MessageDao의 CRUD
*
*/
public interface MessageDao {
  void insertMessage(Message message);

  void deleteMessage(int id);

  void selectAllMessage(String receiver_username);

  void selectIdMessage(String sender_username);

  void selectOldMessage(String receiver_username);

  void selectNewMessage(String receiver_username);
}
