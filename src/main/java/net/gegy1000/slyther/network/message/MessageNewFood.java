package net.gegy1000.slyther.network.message;

import net.gegy1000.slyther.client.SlytherClient;
import net.gegy1000.slyther.game.Color;
import net.gegy1000.slyther.game.Food;
import net.gegy1000.slyther.network.MessageByteBuffer;
import net.gegy1000.slyther.server.SlytherServer;

import java.util.Arrays;

public class MessageNewFood extends SlytherServerMessageBase {
    @Override
    public void write(MessageByteBuffer buffer, SlytherServer server) {
    }

    @Override
    public void read(MessageByteBuffer buffer, SlytherClient client) {
        if (buffer.hasNext(5)) {
            Color color = Color.values()[buffer.readByte()];
            int x = buffer.readShort();
            int y = buffer.readShort();
            int id = y * client.GAME_RADIUS * 3 + x;
            float size = buffer.readByte() / 5.0F;
            Food food = new Food(client, id, x, y, size, messageId == 'b', color);
            food.sx = (int) Math.floor(x / client.SECTOR_SIZE);
            food.sy = (int) Math.floor(y / client.SECTOR_SIZE);
            client.foods.add(food);
        }
    }

    @Override
    public int[] getMessageIds() {
        return new int[]{'b', 'f'};
    }
}