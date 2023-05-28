<template>
  <div>
    <!-- 헤더와 메인화면(로그인 전후) 들어가야함 -->
    <HeaderNav></HeaderNav>

    <!-- 메인 컨텐츠 들어갈 곳 -->
    <main>
      <!-- 로그인 폼 -->
      <div class="login-form form-shadow">
        <h1>로그인</h1>
        <hr />
        <div class="login-form">
          <form>
            <label for="userId">아이디 </label>
            <input
              class="input-box"
              type="text"
              id="userId"
              name="userId"
              v-model="userId"
              required
            />

            <label for="password">비밀번호</label>
            <input
              class="input-box"
              type="password"
              id="password"
              name="password"
              v-model="userPass"
              required
            />
            <br />
            <br />
            <br />

            <div class="submit-btns">
              <input
                type="submit"
                class="login-btn"
                value="로그인"
                @click.prevent="login"
              />
            </div>
          </form>
        </div>
      </div>
      <br />
      <div class="links">
        <span
          class="navigation"
          :class="{ selected: selectedMenu === '메인' }"
          @click="selectMenu('메인')"
        >
          홈으로
        </span>
        <span
          class="navigation"
          :class="{ selected: selectedMenu === '회원가입' }"
          @click="selectMenu('회원가입')"
        >
          회원가입
        </span>
      </div>
    </main>

    <!-- 푸터 들어갈 곳 -->
    <TheFooter></TheFooter>
  </div>
</template>

<script>
import HeaderNav from "@/components/common/HeaderNav.vue";
import TheFooter from "@/components/common/TheFooter.vue";

export default {
  name: "LoginPage",
  data() {
    return {
      userId: "",
      userPass: "",
    };
  },
  computed: {
    selectedMenu() {
      return this.$store.state.hui.selectedMenu;
    },
    user() {
      return this.$store.state.hui.user;
    },
  },

  components: {
    HeaderNav,
    TheFooter,
  },
  methods: {
    // 선택된 단어에 따라 이동
    selectMenu(menu) {
      // 현재 주소와 다른 경우에만 이동
      if (this.selectedMenu !== menu) {
        if (menu === "메인" && this.$route.path !== "/main") {
          this.$router.push("/main");
        } else if (menu === "회원가입" && this.$route.path !== "/signup") {
          this.$router.push("/signup");
        }

        this.$store.dispatch("selectMenu", menu);
      }
    },
    login() {
      if (this.userId.trim().length === 0) {
        alert("아이디를 입력해주세요");
      } else if (this.userPass.trim().length < 4) {
        alert("비밀번호는 4자 이상이어야 합니다.");
      } else {
        const User = {
          userId: this.userId,
          userPass: this.userPass,
        };

        this.$store.dispatch("loginUser", User).then(() => {
          alert(`${this.user.userName}님 환영합니다.`);
        });
      }
    },
  },
};
</script>

<style scoped>
.login-form {
  background-color: #f2f2f2;
  border-radius: 1.5rem;
  padding: 2rem 2rem 1rem 2rem;
  margin: 0 auto;
  max-width: 35rem;
}

.form-shadow {
  box-shadow: 1px 1px 7px rgb(165, 164, 164);
}

body {
  font-family: Arial, sans-serif;
  margin: 0;
  padding: 20px;
}

h1 {
  text-align: center;
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

main {
  margin: 6rem;
}

.login-btn {
  background-color: #aacb73;
  margin-left: 100rem;
  font-size: large;
  box-shadow: 0.7px 0.7px 3px rgb(165, 164, 164);
}

.submit-btns {
  width: 20rem;
  margin-left: 2.5rem;

  display: flex;
  justify-content: center;
}

.links {
  text-align: center;
  justify-content: space-between;
  margin-top: 1rem;
}

.divide.line {
  color: gray;
  border-style: solid;
  border-width: 1px;
  width: 95%;
  margin-left: 2.5%;
}

.navigation {
  border: 2px solid #aacb73;
  background-color: #ffffff;
  padding: 0.5rem;
  text-align: center;
  margin-left: 1rem;
  border-radius: 10px;
  color: #5f9b00;
  transition: 0.4s ease;
  box-shadow: 0.7px 0.7px 3px rgb(165, 164, 164);
}

.navigation:hover {
  cursor: pointer;
  background-color: #5f9b00;
  color: #ffffff;
}
</style>
