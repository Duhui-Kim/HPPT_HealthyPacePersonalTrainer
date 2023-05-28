<template>
  <div>
    <!-- 헤더와 메인화면(로그인 전후) 들어가야함 -->
    <header-nav></header-nav>

    <!-- 메인 컨텐츠 들어갈 곳 -->
    <main>
      <!-- 회원가입 폼 -->
      <div class="regist-form">
        <h1>회원가입</h1>
        <hr />
        <form>
          <div class="choose-file-btn">
            <label class="choose-file-label" for="imgFile"> 프로필 사진 </label>
            <input
              type="file"
              id="image"
              name="image"
              accept="image/*"
              @change="uploadFile"
            />
          </div>

          <label for="userId">아이디 </label>
          <div class="id-form">
            <input
              class="input-box"
              type="text"
              id="userId"
              name="userId"
              ref="userId"
              v-model="userId"
              required
              :disabled="idChecked === 1"
            />
            <button
              type="button"
              id="checkDuplicateButton"
              @click="idCheck"
              :disabled="idChecked === 1"
            >
              중복확인
            </button>
          </div>
          <div v-if="idChecked === 2" class="duplication">
            중복된 아이디가 존재합니다
          </div>
          <div v-else-if="idChecked === 1" class="notDuplication">
            사용 가능한 아이디입니다
          </div>
          <div v-else class="notDuplication">&nbsp;</div>

          <label for="password">비밀번호</label>
          <input
            class="input-box"
            type="password"
            id="password"
            name="password"
            ref="password"
            v-model="userPass"
            required
          />

          <label for="verifyPassword">비밀번호 확인</label>
          <input
            class="input-box"
            type="password"
            id="verifyPassword"
            name="verifyPassword"
            v-model="verifyPassword"
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
          <div v-else class="notsamepass">&nbsp;</div>

          <label for="name">이름 </label>
          <input
            class="input-box"
            type="text"
            id="name"
            name="name"
            v-model="userName"
            required
          />

          <label for="weight">체중 (kg) </label>
          <input
            class="input-box"
            type="number"
            id="weight"
            name="weight"
            v-model="userWeight"
            min="20"
            max="200"
            required
          />

          <label for="targetWeight">목표체중 (kg) </label>
          <input
            class="input-box"
            type="number"
            id="targetWeight"
            name="targetWeight"
            v-model="targetWeight"
            min="20"
            max="200"
            required
          />
          <br />
          <br />
          <label for="gender">성별</label>
          <div class="gender-inputs">
            <input
              type="radio"
              id="male"
              name="gender"
              value="남자"
              v-model="gender"
              required
            />
            <label for="male">남자</label>
            <input
              type="radio"
              id="female"
              name="gender"
              value="여자"
              v-model="gender"
              required
            />
            <label for="female">여자</label>
          </div>
          <br />
          <label for="extype1">운동 종류 선호</label>
          <div class="extype1-inputs">
            <input
              type="radio"
              id="cardio"
              name="extype1"
              value="유산소"
              v-model="extype"
              required
            />
            <label for="cardio">유산소</label>
            <input
              type="radio"
              id="wieght"
              name="extype1"
              value="근력"
              v-model="extype"
              required
            />
            <label for="wieght">근력</label>
          </div>
          <br />
          <label for="extype2">운동 기구 선호</label>
          <div class="extype2-inputs">
            <input
              type="radio"
              id="machine"
              name="extype2"
              value="기구운동"
              v-model="extype2"
              required
            />
            <label for="machine">기구운동 선호</label>
            <input
              type="radio"
              id="barebody"
              name="extype2"
              value="맨몸운동"
              v-model="extype2"
              required
            />
            <label for="barebody">맨몸운동 선호</label>
          </div>

          <br />
          <br />
          <div class="submit-btns">
            <input
              type="submit"
              class="cancel-btn"
              value="취소"
              @click="cancel"
            />
            <input
              type="submit"
              class="regist-btn"
              value="가입"
              @click.prevent="signup"
            />
          </div>
        </form>
      </div>
    </main>

    <!-- 푸터 들어갈 곳 -->
    <the-footer></the-footer>
  </div>
</template>

<script>
import HeaderNav from "@/components/common/HeaderNav.vue";
import TheFooter from "@/components/common/TheFooter.vue";

export default {
  name: "SignUpPage",
  data() {
    return {
      userId: null,
      userPass: null,
      verifyPassword: null,
      userName: "",
      userWeight: 20,
      targetWeight: 20,
      gender: "",
      extype: "",
      extype2: "",
      imgFile: null,
    };
  },

  computed: {
    // 비밀번호 아직 입력 전이면 1 반환 / 같으면 2 반환 / 다르면 3 반환
    passwordCheck() {
      if (this.userPass === null || this.userPass.trim() === "") {
        return 0;
      } else if (this.userPass.trim().length < 4) {
        return 3;
      } else if (this.userPass === this.verifyPassword) {
        return 1;
      } else {
        return 2;
      }
    },
    idChecked() {
      return this.$store.state.hui.idChecked;
    },
    user() {
      return this.$store.state.hui.user;
    },
  },

  methods: {
    uploadFile(event) {
      const file = event.target.files[0];

      this.imgFile = file;
    },
    // 회원가입 method
    signup() {
      if (this.idChecked !== 1) {
        alert("아이디 중복 체크가 필요합니다");
        this.$refs.userId.focus();
      } else if (this.passwordCheck !== 1) {
        alert("비밀번호를 확인해주세요");
        this.$refs.password.focus();
      } else if (parseInt(this.userWeight) <= parseInt(this.targetWeight)) {
        alert("목표 몸무게는 현재 몸무게보다 낮아야 합니다");
      } else if (
        this.userName.trim().length === 0 ||
        this.userWeight < 20 ||
        this.targetWeight < 20 ||
        this.gender.length === 0 ||
        this.extype.length === 0 ||
        this.extype2.length === 0
      ) {
        alert("모든 항목을 입력해야 합니다.");
      } else {
        if (confirm("가입하시겠습니까?")) {
          let exerciseType =
            this.gender + "+" + this.extype + "+" + this.extype2 + "+루틴";
          let gender = this.gender === "남자" ? 1 : 2;

          let User = {
            userId: this.userId,
            userName: this.userName,
            userPass: this.userPass,
            userSex: gender,
            exerciseType: exerciseType,
            userWeight: this.userWeight,
            targetWeight: this.targetWeight,
          };

          // 회원가입 진행
          this.$store
            .dispatch("signUp", User)
            .then(() => {
              // 파일 업로드 진행
              if (this.imgFile) {
                const formData = new FormData();
                formData.append("imgFile", this.imgFile);
                formData.append("userId", this.userId);
                this.$store.dispatch("uploadUserImg", formData);
              }
            })
            // 로그인 진행
            .then(() => {
              this.$store.dispatch("loginUser", User);
            })
            .then(() => {
              alert(`${User.userName}님 환영합니다.`);
            });
        }
      }
    },
    // 취소 method
    cancel() {
      if (confirm("확인을 누르면 가입이 취소됩니다")) {
        this.$router.push("/main");
      }
    },
    // id 중복체크
    idCheck() {
      if (this.userId === null) {
        alert("아이디를 입력해주세요");
        this.$refs.userId.$focus();
      } else if (this.userId.trim().length < 4) {
        alert("아이디는 4자 이상 입력해야합니다");
        this.$store.commit("SET_ID_CHECK", 0);
        this.$refs.userId.focus();
      } else {
        if (
          confirm("중복체크 완료 후 아이디 변경은 불가능합니다. 계속할까요?")
        ) {
          this.$store.dispatch("checkId", this.userId);

          this.$nextTick(() => {
            if (this.$store.state.hui.idChecked === 1) {
              this.$refs.password.focus();
            } else {
              this.$refs.userId.focus();
            }
          });
        }
      }
    },
  },

  created() {
    this.$store.commit("SET_ID_CHECK", 0);
  },
  components: {
    HeaderNav,
    TheFooter,
  },
};
</script>

<style scoped>
.regist-form {
  background-color: #f2f2f2;
  border-radius: 1.5rem;
  padding: 2rem;
  margin: 0 auto;
  max-width: 35rem;
  box-shadow: 1px 1px 7px rgb(165, 164, 164);
}

body {
  font-family: Arial, sans-serif;
  margin: 0;
  padding: 20px;
}

main {
  margin: 5rem;
}

h1 {
  text-align: center;
}

.choose-file-btn {
  display: flex;
  justify-content: center;
  align-items: center;
  border: none;
  /* box-shadow: none; */
  /* box-shadow: 0.7px 0.7px 3px rgb(165, 164, 164); */
}

.choose-file-label {
  cursor: pointer;
  background-color: #e99292;
  padding: 0.4rem 0.625rem;
  border-radius: 10px;
  border: none;

  color: white;
  height: 2.2rem;
  width: 5rem;
  font-size: 14px;

  display: flex;
  align-items: center;
  justify-content: center;

  box-shadow: 0.7px 0.7px 3px rgb(165, 164, 164);
  margin-top: 0.3rem;
}

.choose-file-btn:hover .choose-file-label {
  color: #ffffff;
  background-color: #6e6e6e;
}

.choose-file-btn input[type="file"] {
  opacity: 0;
  position: absolute;
  cursor: pointer;
  width: 5rem;
  height: 2.2rem;
}

form {
  max-width: 400px;
  margin: 20px auto;
}

label {
  display: block;
  margin-bottom: 5px;
}
.input-box {
  border: 1px solid white;
  border-radius: 7px;
  height: 1.2rem;
  box-shadow: 1px 1px 3px rgb(201, 200, 200);
}
input[type="text"],
input[type="password"] {
  width: 100%;
  padding: 10px;
  margin-bottom: 10px;
  border: 1px solid #ccc;
}
.id-form {
  display: flex;
  align-items: center;
}

.id-form label {
  flex-shrink: 0;
  margin-right: 0.625rem;
}

.id-form input[type="text"] {
  flex-grow: 1;
  margin-right: 0.625rem;
}

.id-form button {
  flex-shrink: 0;
}

.duplication,
.notsamepass {
  color: red;
  text-align: end;
  font-size: small;
}

.notDuplication,
.samePass {
  color: green;
  text-align: end;
  font-size: small;
}

#checkDuplicateButton {
  background-color: #e9e1cad3;
  border: 7px solid #e9e1cad3;
  border-radius: 8px;
  color: #555;
  border: none;
  height: 34px;
  margin: 0 0 0.625rem 1rem;
  padding: 5px 10px;
  font-size: 16px;
  cursor: pointer;
  transition: background-color 0.3s ease;
  box-shadow: 0.7px 0.7px 3px rgb(165, 164, 164);
}

#checkDuplicateButton:hover {
  background-color: #ddd;
}

#checkDuplicateButton:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}

.gender-inputs,
.extype1-inputs,
.extype2-inputs {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

.gender-inputs label,
.extype1-inputs label,
.extype2-inputs label {
  margin-right: 10px;
}

.gender-inputs input[type="radio"],
.extype1-inputs input[type="radio"],
.extype2-inputs input[type="radio"] {
  margin-right: 5px;
}

#weight,
#targetWeight {
  width: calc(100% - 14px);
  margin: 10px 0;
  padding: 10px;
}

input[type="submit"] {
  margin: 0 auto;
  width: 30%;
  padding: 10px;
  color: white;
  border: none;
  cursor: pointer;
  border-radius: 7px;
}

input[type="submit"]:hover {
  background-color: #cde990;
}

.regist-btn {
  background-color: #aacb73;
  box-shadow: 0.7px 0.7px 3px rgb(165, 164, 164);
}

.cancel-btn {
  background-color: rgb(238, 90, 85);
  box-shadow: 0.7px 0.7px 3px rgb(165, 164, 164);
}

.submit-btns {
  display: flex;
  justify-content: center;
}

.divide.line {
  color: gray;
  border-style: solid;
  border-width: 1px;
  width: 95%;
  margin-left: 2.5%;
}
</style>
