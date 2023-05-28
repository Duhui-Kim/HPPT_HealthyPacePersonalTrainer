<template>
  <div class="edit-info">
    <h2>회원정보 수정</h2>
    <hr />
    <br />

    <form class="input-form">
      <div>
        <span class="edit-info-span">이름</span>
        <input
          class="input-box"
          type="text"
          id="name"
          name="name"
          v-model="user.userName"
          required
        />
      </div>
      <br />
      <div class="select-type-area">
        <span class="edit-info-span input-exercise">운동타입</span>
        <div class="select-type">
          <span class="select-type-extype extype1">
            <label for="cardio">유산소</label>
            <input
              class="select-type-extype-radio"
              type="radio"
              id="cardio"
              name="extype1"
              value="유산소"
              v-model="exerType1"
              required
            />

            <label for="wieght">근력</label>
            <input
              class="select-type-extype-radio"
              type="radio"
              id="wieght"
              name="extype1"
              value="근력"
              v-model="exerType1"
              required
            />
          </span>
          <span class="select-type-extype extype2">
            <label for="machine">기구</label>
            <input
              class="select-type-extype-radio"
              type="radio"
              id="machine"
              name="extype2"
              value="기구운동"
              v-model="exerType2"
              required
            />

            <label for="barebody">맨몸</label>
            <input
              class="select-type-extype-radio"
              type="radio"
              id="barebody"
              name="extype2"
              value="맨몸운동"
              v-model="exerType2"
              required
            />
          </span>
        </div>
      </div>
      <br />
      <div>
        <span class="edit-info-span">기존 비밀번호</span>
        <input
          class="input-box"
          type="password"
          ref="oldpassword"
          id="old-password"
          name="old-password"
          v-model="userPass"
          required
        />
      </div>
      <br />
      <div>
        <span class="edit-info-span">새 비밀번호</span>
        <input
          class="input-box"
          type="password"
          id="new-password"
          name="new-password"
          v-model="userUpdatePass"
          required
        />
      </div>
      <br />
      <div>
        <span class="edit-info-span">새 비밀번호 확인</span>
        <input
          class="input-box"
          type="password"
          ref="newpassword"
          id="confirm-new-password"
          name="confirm-new-password"
          v-model="userUpdatePassConfirm"
          required
        />
        <div v-if="passwordCheck === 1" class="samePass">
          비밀번호가 일치합니다
        </div>
        <div v-else-if="passwordCheck === 2" class="notsamepass">
          비밀번호가 일치하지 않습니다
        </div>
        <div v-else-if="passwordCheck === 3" class="notsamepass">
          비밀번호를 4자 이상 입력해주세요
        </div>
        <div v-else-if="passwordCheck === 0" class="blankArea">&nbsp;</div>
      </div>
      <br />
      <div class="info-btns">
        <input
          class="edit-info-btn btn-primary"
          type="submit"
          value="저장"
          @click.prevent="updateUser"
        />
        <input
          class="edit-info-btn btn-danger"
          type="submit"
          value="취소"
          @click.prevent="updateCancel"
        />
      </div>
      <!-- 회원정보 수정 눌렀을 때 나오게 하기 -->
      <div class="choose-file-btn">
        <label for="ex_file">수정</label>
        <input type="file" id="ex_file" accept="image/*" @change="uploadFile" />
      </div>
    </form>
  </div>
</template>

<script>
export default {
  name: "UserInfoEdit",

  data() {
    return {
      exerType1: "",
      exerType2: "",
      userPass: "",
      userUpdatePass: "",
      userUpdatePassConfirm: "",
      imgFile: null,
    };
  },

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

    passwordCheck() {
      if (this.userUpdatePass === null || this.userUpdatePass.trim() === "") {
        return 0;
      } else if (this.userUpdatePass.trim().length < 4) {
        return 3;
      } else if (this.userUpdatePass === this.userUpdatePassConfirm) {
        return 1;
      } else {
        return 2;
      }
    },
  },

  methods: {
    updateCancel() {
      this.$store.commit("SET_IS_UPDATING", true);
    },
    uploadFile(event) {
      const file = event.target.files[0];

      this.imgFile = file;
    },
    updateUser() {
      if (this.userPass.trim().length === 0) {
        alert("비밀번호를 입력해주세요");
        this.$refs.oldpassword.focus();
      } else if (this.passwordCheck === 2 || this.passwordCheck === 3) {
        alert("새 비밀번호를 확인해주세요");
        this.$refs.newpassword.focus();
      } else {
        if (!confirm("저장하시겠습니까?")) return;

        let exerciseType =
          this.userSex + "+" + this.exerType1 + "+" + this.exerType2 + "+루틴";
        let userUpdatePass =
          this.userUpdatePass.trim() === "" ? null : this.userUpdatePass;

        let User = {
          userId: this.user.userId,
          userPass: this.userPass,
          userName: this.user.userName,
          exerciseType: exerciseType,
          userUpdatePass: userUpdatePass,
          userImg: this.user.userImg,
          remainKcal: this.user.remainKcal,
        };

        this.$store
          .dispatch("updateUser", User)
          .then(() => {
            if (this.imgFile) {
              const formData = new FormData();
              formData.append("imgFile", this.imgFile);
              formData.append("userId", this.user.userId);
              this.$store.dispatch("uploadUserImg", formData);
            }
          })
          .then(() => {
            let updateUser = this.$store.state.hui.user;
            updateUser.userName = User.userName;
            updateUser.exerciseType = User.exerciseType;
            this.$store.commit("SET_IS_UPDATING", true);
          })
          .then(() => {
            setTimeout(() => {
              this.$store.dispatch("getUserData", this.user.userId);
            }, 100);
          });
      }
    },
  },

  mounted() {
    let type = this.$store.state.hui.user.exerciseType.split("+");
    this.exerType1 = type[1];
    this.exerType2 = type[2];
    this.user = this.$store.state.hui.user;
    if (this.user.userSex === 1) {
      this.userSex = "남자";
    } else {
      this.userSex = "여자";
    }
    this.passwordCheck = 0;
  },
};
</script>

<style scoped>
.input-form {
  width: 352px;
  height: 313px;
  display: flex;
  overflow: visible;
}
.choose-file-btn {
  transform: translate(-50%, -50%);
  display: flex;
  flex-direction: column-reverse;
  width: 5rem;
  margin-top: -1.5rem;
  margin-right: 58rem;
}
.choose-file-btn label {
  display: inline-block;
  padding: 0.5em 0.75em;
  color: #ffffff;
  font-weight: bold;
  font-size: inherit;
  line-height: normal;
  vertical-align: middle;
  background-color: #fc7f7f;
  cursor: pointer;
  border: 1px solid #ebebeb;
  border-bottom-color: #e2e2e2;
  border-radius: 0.4em;
}

.choose-file-btn label:hover {
  background-color: #fd4b4b;
}

.choose-file-btn input[type="file"] {
  /* 파일 필드 숨기기 */
  position: absolute;
  width: 1px;
  height: 1px;
  padding: 0;
  margin: -1px;
  overflow: hidden;
  clip: rect(0, 0, 0, 0);
  border: 0;
}

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
  overflow: visible;
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
  position: relative;
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
  border: 1px solid white;
  border-radius: 7px;
  height: 1.2rem;
  box-shadow: 1px 1px 3px rgb(201, 200, 200);
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

.samePass {
  text-align: end;
  font-size: small;
  color: blue;
}

.notsamepass {
  text-align: end;
  font-size: small;
  color: red;
}

.blankArea {
  text-align: end;
  font-size: small;
  color: #f2f2f2;
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
