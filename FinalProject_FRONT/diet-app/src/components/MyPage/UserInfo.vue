<template>
  <div class="edit-info">
    <h2>회원정보</h2>
    <hr />
    <br />
    <div class="edit-info-user">
      <span class="edit-info-span">이름</span>
      <span class="edit-info-span-value">{{ user.userName }}</span>

      <span class="edit-info-span">성별</span>
      <span class="edit-info-span-value">{{ userSex }}</span>

      <span class="edit-info-span">운동타입</span>
      <span class="edit-info-span-value">{{ exerType }}</span>

      <span class="edit-info-span">팔로워 수</span>
      <span class="edit-info-span-value">{{ follower.length }}명</span>
      <div class="info-btn-edit">
        <input
          class="edit-info-btn"
          type="submit"
          value="회원정보 수정"
          @click="updateForm"
        />
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "UserInfo",
  computed: {
    user() {
      return this.$store.state.hui.user;
    },
    userSex() {
      if (this.user.userSex === 1) {
        return "남자";
      } else {
        return "여자";
      }
    },
    exerType() {
      const type = this.user.exerciseType.split("+");
      return type[1] + "/" + type[2];
    },
    follower() {
      return this.$store.state.hui.fans;
    },
  },
  methods: {
    updateForm() {
      this.$store.commit("SET_IS_UPDATING", false);
    },
  },
};
</script>

<style scoped>
.edit-info-btn:hover,
.edit-img-btn:hover {
  cursor: pointer;
}
.speak-number {
  padding: 0.2rem 0.5rem;
  border-radius: 7px;
  text-align: center;
  font-weight: bold;
}

.edit-info {
  background-color: #f2f2f2;
  padding: 1rem;
  border-radius: 10px;
  height: 25rem;
  box-shadow: 1px 1px 7px rgb(165, 164, 164);
}

.edit-info-span {
  display: inline-block;
  width: 8rem;
  text-align: center;
  font-weight: bold;
}

.edit-info form {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.edit-info-user {
  display: grid;
  justify-content: center;
  padding-left: 1.5rem;
  grid-template-columns: repeat(2, 10rem);
  grid-template-rows: repeat(5, 3.7rem);
}

.choose-file-btn {
  grid-row: 4 / span 1;
  display: flex;
  justify-content: center;
  align-items: center;
}
.edit-img-btn {
  text-align: center;
  color: white;
  background-color: #aacb73;
  padding: 0.4rem 0.625rem;
  border-radius: 4px;
  border: #aacb73;
  cursor: pointer;
}

.edit-info-btn:hover {
  background-color: #7bb126;
}
.link-margin {
  margin: 0 0.6rem;
  display: inline-block;
}

.select-type-area {
  display: grid;
  grid-template-columns: repeat(5, 3.5rem);
  gap: 0.5rem;
}

.select-type {
  display: grid;
  grid-template-rows: repeat(2, 1.2rem);
  grid-column: 3 / span 3;
  gap: 0.2rem;
  padding-left: 0.3rem;
}

.info-btn-edit {
  margin-left: 6.7rem;
}
.input-exercise {
  padding-top: 0.5rem;
}

.select-type-extype {
  display: grid;
  grid-template-columns: repeat(4, 3rem);
  text-align: center;
}

.select-type-extype-radio {
  border: max(2px, 0.1em) solid gray;
  border-radius: 50%;
  width: 1.25rem;
  height: 0.9rem;
  transition: border 0.5s ease-in-out;
  margin-left: 0.7rem;
  width: 0.8rem;
}

.input-box {
  border: 1px solid #ffd4d4;
  border-radius: 7px;
  height: 1.2rem;
}

.btn-primary {
  background-color: rgb(136, 136, 255);
}

.btn-primary:hover {
  background-color: rgb(94, 94, 240);
}

.btn-danger {
  background-color: rgb(255, 139, 139);
}

.btn-danger:hover {
  background-color: rgb(245, 80, 80);
}

.info-btns {
  display: flex;
  justify-content: space-around;
  width: 8rem;
}

.edit-info-btn {
  text-align: center;
  color: white;
  background-color: #aacb73;
  padding: 0.4rem 0.625rem;
  border-radius: 4px;
  border: #aacb73;
  cursor: pointer;
  box-shadow: 0.7px 0.7px 3px rgb(165, 164, 164);
}
</style>
