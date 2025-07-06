<template>
  <div class="container">
    <h2>用户列表</h2>
    <div class="toolbar">
      <div class="search-wrap">
        <svg class="search-icon" viewBox="0 0 20 20"><circle cx="9" cy="9" r="7" stroke="#b0b8c9" stroke-width="2" fill="none"/><line x1="14" y1="14" x2="19" y2="19" stroke="#b0b8c9" stroke-width="2" /></svg>
        <input v-model="keyword" class="search" placeholder="搜索用户名..." />
      </div>
    </div>
    <ul class="user-list">
      <li v-for="u in filteredUsers" :key="u.id" class="user-item">
        <span class="uid" :title="u.id">{{ u.id }}</span>
        <span class="uname" :title="u.name">{{ u.name }}</span>
        <span class="upwd" :title="u.password">{{ u.password }}</span>
        <button class="btn edit" @click="editUser(u)">编辑</button>
        <button class="btn delete" @click="deleteUser(u.id)">删除</button>
      </li>
      <li v-if="filteredUsers.length===0" class="empty-tip">没有符合条件的数据</li>
    </ul>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import axios from 'axios'

const users = ref([])
const keyword = ref('')

onMounted(async () => {
  const res = await axios.get('/api/users')
  users.value = res.data
})

const filteredUsers = computed(() =>
    users.value.filter(u => u.name.includes(keyword.value.trim()))
)

function deleteUser(id) {
  if (!confirm('确定要删除吗？')) return
  users.value = users.value.filter(u => u.id !== id)
}
function editUser(user) {
  alert('编辑功能开发中：' + user.name)
}
</script>

<style scoped>
.container {
  max-width: 500px;
  min-width: 280px;
  margin: 48px auto;
  padding: 32px 18px 28px 18px;
  background: #fff;
  border-radius: 14px;
  box-shadow: 0 2px 18px #dbe3f51c;
  overflow: hidden;
}
h2 {
  margin-bottom: 20px;
  font-weight: 600;
  color: #334268;
  font-size: 21px;
  letter-spacing: 1px;
}
.toolbar {
  margin-bottom: 16px;
}
.search-wrap {
  position: relative;
  width: 100%;
}
.search-icon {
  width: 18px;
  height: 18px;
  position: absolute;
  top: 50%;
  left: 10px;
  transform: translateY(-50%);
  pointer-events: none;
}
.search {
  width: 100%;
  padding: 8px 12px 8px 33px;
  border: 1px solid #dde3ed;
  border-radius: 7px;
  font-size: 15px;
  background: #f7fafd;
  transition: border 0.2s;
}
.search:focus {
  border-color: #518be5;
  background: #f3f7fb;
}
.user-list {
  list-style: none;
  margin: 0; padding: 0;
}
.user-item {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 10px 0;
  border-bottom: 1px solid #f2f3f6;
  font-size: 15px;
  transition: background 0.13s;
  overflow-x: auto;
}
.user-item:hover {
  background: #f5f8fd;
}
.user-item:last-child { border-bottom: none; }
.uid, .uname, .upwd {
  min-width: 48px;
  max-width: 120px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  display: inline-block;
  padding: 0 3px;
  color: #333d5d;
  font-size: 15px;
}
.uname { font-weight: 500; color: #2458b3; }
.upwd { color: #b19161; }
.btn {
  padding: 3px 14px;
  border: none;
  border-radius: 5px;
  font-size: 14px;
  margin-left: 2px;
  cursor: pointer;
  background: #eef3fa;
  color: #375b9e;
  transition: background 0.12s, color 0.12s;
}
.btn.edit:hover {
  background: #d5e4fa;
  color: #214381;
}
.btn.delete {
  background: #faeded;
  color: #c54545;
}
.btn.delete:hover {
  background: #f7dcdc;
  color: #a63434;
}
.empty-tip {
  color: #adb3bd;
  padding: 15px 4px 8px 4px;
  text-align: center;
  font-size: 15px;
  letter-spacing: 1px;
}
@media (max-width: 600px) {
  .container { padding: 12px 1vw 10px 1vw; max-width: 99vw; }
  .user-item, .toolbar { font-size: 13.5px;}
  .uid, .uname, .upwd { max-width: 60px; }
}
</style>
