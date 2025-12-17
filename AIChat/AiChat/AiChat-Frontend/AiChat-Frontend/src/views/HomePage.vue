<template>
  <div class="home-page">
    <div class="container">
      <h1 class="title">AI 脑筋急转弯</h1>
      <a-button type="primary" class="start-button" @click="startGame">
        开始游戏
      </a-button>
    </div>

  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router';

const router = useRouter();

const startGame = () => {
  router.push('/chat');
};
</script>

<style scoped>
.home-page {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
  background-color: #f5f5f5;
}

.title {
  font-size: 48px;
  font-weight: 600;
  margin-bottom: 80px;
  color: #333;
  text-align: center;
}

.start-button {
  width: 400px;
  height: 90px;
  font-size: 32px;
  border-radius: 60px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  background-color: #1890ff;
}

.container {
  margin-left: auto;
  margin-right: auto;
  padding: 100px;
  background-color: #ffffff;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}
</style>
