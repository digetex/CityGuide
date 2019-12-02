package com.ra.cityguide.bot;

import com.ra.cityguide.Entity.City;
import com.ra.cityguide.repositories.CityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.List;

public class Bot extends TelegramLongPollingBot {

    private static Integer messageId;
    private static String chatId;

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            sendMsg(message, "Hi. I am city bot. I remember you)");
        }
    }

    public void sendCity(String str) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setReplyToMessageId(messageId);
        messageId++;
        sendMessage.setText(str);
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendMsg(Message message, String str) {
        SendMessage sendMessage = new SendMessage();
        chatId = message.getChatId().toString();
        sendMessage.setChatId(chatId);
        messageId = message.getMessageId();
        sendMessage.setReplyToMessageId(messageId);
        messageId++;
        sendMessage.setText(str);
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "City Guide";
    }

    @Override
    public String getBotToken() {
        return "801730328:AAEaFPhjl-mW4u68APd2_KZerp7r_xb2r8I";
    }

    public Bot() {
    }

    public boolean CheckUsersNotNull() {
        if (messageId != null & chatId != "")
            return true;

        else
            return false;
    }

}
