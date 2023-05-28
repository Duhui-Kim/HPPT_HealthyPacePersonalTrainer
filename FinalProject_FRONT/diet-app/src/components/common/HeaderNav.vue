<template>
  <header>
    <div class="logo" @click="selectMenu('메인')">
      <img class="logoImg" src="@/assets/logo_HPPT.png" alt="로고" />
    </div>
    <nav v-if="LoginCheck">
      <ul class="menu">
        <li
          :class="{ selected: selectedMenu === '식단' }"
          @click="selectMenu('식단')"
        >
          식단
        </li>
        <li
          :class="{ selected: selectedMenu === '운동영상' }"
          @click="selectMenu('운동영상')"
        >
          운동영상
        </li>
        <li
          :class="{ selected: selectedMenu === '게시판' }"
          @click="selectMenu('게시판')"
        >
          게시판
        </li>
        <li
          :class="{ selected: selectedMenu === '마이페이지' }"
          @click="selectMenu('마이페이지')"
        >
          마이페이지
        </li>
        <li @click="logout">로그아웃</li>
      </ul>
    </nav>
    <nav v-else>
      <ul class="menu">
        <li
          :class="{ selected: selectedMenu === '로그인' }"
          @click="selectMenu('로그인')"
        >
          로그인
        </li>
        <li
          :class="{ selected: selectedMenu === '회원가입' }"
          @click="selectMenu('회원가입')"
        >
          회원가입
        </li>
      </ul>
    </nav>
  </header>
</template>

<script>
export default {
  name: "HeaderNav",

  methods: {
    // 로그아웃 method
    logout() {
      this.$store.dispatch("logout");
      // 로그아웃 완료 시 처음 페이지로 이동
      if (!this.LoginCheck) {
        this.$router.push("/");
      }
    },
    // 메뉴바 선택 method
    // 선택된 단어에 따라 이동
    selectMenu(menu) {
      // 현재 주소와 다른 경우에만 이동
      if (this.selectedMenu !== menu) {
        if (menu === "메인" && this.$route.path !== "/main") {
          this.$router.push("/main");
        } else if (menu === "식단" && this.$route.path !== "/meal") {
          this.$router.push("/meal");
        } else if (menu === "운동영상" && this.$route.path !== "/video") {
          this.$router.push("/video");
        } else if (menu === "게시판" && this.$route.path !== "/board") {
          this.$router.push("/board");
        } else if (menu === "마이페이지" && this.$route.path !== "/mypage") {
          this.$router.push("/mypage");
        } else if (menu === "로그인" && this.$route.path !== "/login") {
          this.$router.push("/login");
        } else if (menu === "회원가입" && this.$route.path !== "/signup") {
          this.$router.push("/signup");
        }

        this.$store.dispatch("selectMenu", menu);
      }
    },
  },
  computed: {
    LoginCheck() {
      let JWT = this.$store.state.hui.JWT;
      if (JWT == null) {
        return false;
      } else {
        return true;
      }
    },
    selectedMenu() {
      return this.$store.state.hui.selectedMenu;
    },
  },
  components: {},
};
</script>

<!-- 전체 마진 없애기 -->
<style>
body {
  margin: 0;
  background-color: #ffffe8;
}
a,
router-link {
  text-decoration: none;
}

/* 폰트 */
@font-face {
  font-family: "S-CoreDream-3Light";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_six@1.2/S-CoreDream-3Light.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}

* {
  font-family: "S-CoreDream-3Light";
}
</style>

<!-- 이 컴포넌트에서만 적용할 스타일 -->
<style scoped>
header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1.25rem;
  border: 0;
  background-color: white;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
}

.logo:hover {
  cursor: pointer;
}

.logoImg {
  width: 100px;
  height: 100px;
}

.menu {
  list-style: none;
  display: flex;
}

.menu li {
  margin-left: 0.625rem;
  text-decoration: none;
  color: #333;
  padding: 0.3125rem;
  border-radius: 5px;
}

.menu li:hover,
.menu li.selected {
  cursor: pointer;
  background-color: rgb(255, 255, 255);
  color: #eb42ce;
  font-weight: bolder;
}
</style>
