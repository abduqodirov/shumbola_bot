import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;

public class ShumbolaBot extends TelegramLongPollingBot {

    private static String paqVideoId = "BAACAgIAAxkDAAIBtl9Q_anb6JZlvt20LqeasGvjJxPRAAJ4CQACXm2BSvhlNO-GPTP-GwQ";

    private ArrayList<String> triggerWords = new ArrayList<String>();

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {

            String receivedMessage = update.getMessage().getText();

            String responseMessage = "";

            if (triggerWords.isEmpty()) {
                triggerWords.add("surish");
            }

            if (receivedMessage.equals("/start")) {
                responseMessage = Signs.start;

                SendMessage sendMessage = new SendMessage()
                        .setChatId(update.getMessage().getChatId())
                        .setText(responseMessage)
                        .setReplyToMessageId(update.getMessage().getMessageId())
                        .disableNotification();

                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

            }


            addNewTriggerWords(update.getMessage());

            for (int i=0; i < triggerWords.size(); i++) {
                //Tekshirish juda ko'p vaqt olvormasligi uchun uzun messagelardan voz kechamiz
                if (receivedMessage.length() < 200) {

                    if (receivedMessage.contains(triggerWords.get(i))) {
                        sendPaqVideo(update.getMessage());
                    }

                }
            }

        }
    }

    private void addNewTriggerWords(Message message) {

        if (message.getFrom().getId() == 215932105) {

            if (message.getText().contains("/add")) {

                String newTrigger = message.getText().substring(5);
                log(newTrigger + " added to trigger words");
                triggerWords.add(newTrigger);

            }

        }

    }

    private void sendPaqVideo(Message message) {
        SendVideo sendRepeatedVideo = new SendVideo()
                .setDuration(31)
                .setHeight(682)
                .setWidth(1280)
                .setVideo(paqVideoId)
                .enableNotification()
                .setChatId(message.getChatId())
                .setReplyToMessageId(message.getMessageId());

        try {
            execute(sendRepeatedVideo);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "Shumbola_AI_bot";
    }

    @Override
    public String getBotToken() {
        return "1347287348:AAFVmqv2P_yMPvNNIXWElvN0agdu66eP1n8";
    }

    public static void log(String message) {
        System.out.println(message);
    }

}
