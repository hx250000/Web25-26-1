<template>
  <div class="chat-page">
    <div class="sidebar">
      <div class="sidebar-title">历史对话</div>
      <div class="room-list">
        <div
          v-for="room in roomList"
          :key="room.id"
          class="room-item"
          :class="{ active: room.id === currentRoomId }"
          @click="switchRoom(room.id)"
        >
          对话 {{ room.id }}
        </div>
      </div>
    </div>

    <div class="main-content">
      <div class="chat-header">
        <h2>AI 脑筋急转弯</h2>
        <div class="room-id">房间号: {{ currentRoomId }}</div>
      </div>

      <div class="chat-messages" ref="messagesContainer">
        <div
          v-for="(message, index) in messages"
          :key="index"
          class="message-wrapper"
          :class="message.isUser ? 'user-message-wrapper' : 'ai-message-wrapper'"
        >
          <div class="avatar" :class="message.isUser ? 'user-avatar' : 'ai-avatar'">
            {{ message.isUser ? '我' : 'AI' }}
          </div>
          <div class="message-bubble">
            {{ message.content }}
          </div>
        </div>
      </div>

      <div class="chat-controls">
        <div class="control-buttons">
          <a-button
            type="primary"
            :disabled="gameStarted&&!gameEnded"
            @click="startGame"
          >
            开始
          </a-button>
          <a-button
            :disabled="gameEnded&&!gameStarted"
            @click="endGame"
          >
            结束游戏
          </a-button>
        </div>
        <div class="input-area">
          <a-input
            v-model:value="inputMessage"
            placeholder="请输入内容"
            @pressEnter="sendMessage"
            :disabled="!gameStarted || gameEnded"
          />
          <a-button
            type="primary"
            @click="sendMessage"
            :disabled="!gameStarted || gameEnded"
          >
            发送
          </a-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, nextTick } from 'vue';
import { chatApi, type ChatRoom } from '../api/chat';
import { message } from 'ant-design-vue';

interface Message {
  content: string;
  isUser: boolean;
}

const currentRoomId = ref<number>(0);
const messages = ref<Message[]>([]);
const inputMessage = ref('');
const gameStarted = ref(false);
const gameEnded = ref(false);
const roomList = ref<ChatRoom[]>([]);
const messagesContainer = ref<HTMLElement | null>(null);

const generateRoomId = () => {
  return Math.floor(Math.random() * 1000000);
};

const loadRoomList = async () => {
  try {
    roomList.value = await chatApi.getRoomList();
  } catch (error) {
    console.error('加载房间列表失败:', error);
  }
};

const scrollToBottom = async () => {
  await nextTick();
  if (messagesContainer.value) {
    messagesContainer.value.scrollTop = messagesContainer.value.scrollHeight;
  }
};

const startGame = async () => {
  try {
    const aiResponse = await chatApi.sendMessage(currentRoomId.value, '开始');

    messages.value.push({
      content: '开始',
      isUser: true
    });

    messages.value.push({
      content: aiResponse,
      isUser: false
    });

    gameStarted.value = true;

    if (aiResponse.includes('游戏已结束')) {
      gameEnded.value = true;
      gameStarted.value = false;
    }

    await scrollToBottom();
    await loadRoomList();
  } catch (error) {
    message.error('发送消息失败，请检查后端服务是否启动');
    console.error('发送消息失败:', error);
  }
};

const sendMessage = async () => {
  if (!inputMessage.value.trim()) {
    return;
  }

  const userMessage = inputMessage.value;
  inputMessage.value = '';

  try {
    messages.value.push({
      content: userMessage,
      isUser: true
    });

    await scrollToBottom();

    const aiResponse = await chatApi.sendMessage(currentRoomId.value, userMessage);

    messages.value.push({
      content: aiResponse,
      isUser: false
    });

    if (aiResponse.includes('游戏已结束')) {
      gameEnded.value = true;
    }

    await scrollToBottom();
    await loadRoomList();
  } catch (error) {
    message.error('发送消息失败，请检查后端服务是否启动');
    console.error('发送消息失败:', error);
  }
};

const endGame = async () => {
  try {
    const aiResponse = await chatApi.sendMessage(currentRoomId.value, '结束');

    messages.value.push({
      content: '结束',
      isUser: true
    });

    messages.value.push({
      content: aiResponse,
      isUser: false
    });

    gameEnded.value = true;
    gameStarted.value = false
    await scrollToBottom();
    await loadRoomList();
  } catch (error) {
    message.error('发送消息失败，请检查后端服务是否启动');
    console.error('发送消息失败:', error);
  }
};

const switchRoom = (roomId: number) => {
  currentRoomId.value = roomId;
  messages.value = [];
  gameStarted.value = false;
  gameEnded.value = false;
  inputMessage.value = '';
};

onMounted(() => {
  currentRoomId.value = generateRoomId();
  loadRoomList();
});
</script>

<style scoped>
.chat-page {
  display: flex;
  height: 100vh;
  background-color: #f5f5f5;
}

.sidebar {
  width: 250px;
  background-color: #fff;
  border-right: 1px solid #e8e8e8;
  display: flex;
  flex-direction: column;
}

.sidebar-title {
  padding: 20px;
  font-size: 18px;
  font-weight: 600;
  border-bottom: 1px solid #e8e8e8;
}

.room-list {
  flex: 1;
  overflow-y: auto;
}

.room-item {
  padding: 16px 20px;
  cursor: pointer;
  border-bottom: 1px solid #f0f0f0;
  transition: background-color 0.3s;
}

.room-item:hover {
  background-color: #f5f5f5;
}

.room-item.active {
  background-color: #e6f7ff;
  color: #1890ff;
}

.main-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  background-color: #fff;
}

.chat-header {
  padding: 16px 24px;
  border-bottom: 1px solid #e8e8e8;
  background-color: #fff;
}

.chat-header h2 {
  margin: 0 0 8px 0;
  font-size: 20px;
}

.room-id {
  font-size: 14px;
  color: #666;
}

.chat-messages {
  flex: 1;
  padding: 24px;
  overflow-y: auto;
  background-color: #fafafa;
}

.message-wrapper {
  display: flex;
  margin-bottom: 16px;
  gap: 12px;
}

.ai-message-wrapper {
  justify-content: flex-start;
}

.user-message-wrapper {
  justify-content: flex-end;
}

.avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 14px;
  font-weight: 600;
  flex-shrink: 0;
}

.ai-avatar {
  background-color: #1890ff;
  color: #fff;
}

.user-avatar {
  background-color: #52c41a;
  color: #fff;
  order: 2;
}

.message-bubble {
  max-width: 60%;
  padding: 12px 16px;
  border-radius: 8px;
  word-wrap: break-word;
  line-height: 1.5;
}

.ai-message-wrapper .message-bubble {
  background-color: #fff;
  border: 1px solid #e8e8e8;
}

.user-message-wrapper .message-bubble {
  background-color: #1890ff;
  color: #fff;
  order: 1;
}

.chat-controls {
  padding: 16px 24px;
  border-top: 1px solid #e8e8e8;
  background-color: #fff;
}

.control-buttons {
  display: flex;
  gap: 12px;
  margin-bottom: 12px;
}

.input-area {
  display: flex;
  gap: 12px;
}

.input-area .ant-input {
  flex: 1;
}
</style>
