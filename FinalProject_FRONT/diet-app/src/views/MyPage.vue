<template>
  <div>
    <!-- 헤더와 메인화면(로그인 전후) 들어가야함 -->
    <HeaderNav></HeaderNav>

    <!-- 메인 컨텐츠 들어갈 곳 -->
    <main class="main">
      <div class="user-info">
        <div class="edit-img">
          <img
            v-if="user.userImg === null"
            class="profile-img"
            src="@/assets/프로필 기본 이미지.png"
            alt="프로필 사진"
          />
          <img
            v-else-if="user.userImg"
            class="profile-img"
            :src="url"
            :key="user.userImg"
            alt="프로필 사진"
          />
        </div>

        <!-- 기본 회원 정보 -->
        <user-info v-if="isUpdating"></user-info>

        <!-- 회원정보 수정 -->
        <user-info-edit v-else></user-info-edit>
      </div>

      <div class="friend-info">
        <div class="friend-list">
          <h2>팔로잉 목록</h2>
          <hr />
          <br />
          <div class="speak-numbers"></div>
          <ul class="friend-list-area">
            <li v-for="(idol, index) in idols" :key="index">
              <div class="idol-list">
                <img
                  class="idol-list-img"
                  src="../assets/프로필 기본 이미지.png"
                  v-if="idol.userImg === null"
                />
                <img
                  class="idol-list-img"
                  :src="`http://localhost:9999/user/img/${idol.userId}`"
                  v-else
                />
                <span class="idol-list-id">{{ idol.userId }}</span>
                <span class="idol-list-name">{{ idol.userName }}</span>
                <button
                  class="idol-list-btn delete-btn"
                  @click="unfollow(idol.userId)"
                >
                  삭제
                </button>
              </div>
            </li>
          </ul>
        </div>

        <div class="friend-search">
          <h2>친구 검색</h2>
          <hr />
          <br />
          <div class="search-container">
            <input
              class="search-friend"
              type="text"
              id="friend-search"
              v-model="searchWord"
              ref="search"
              v-on:keyup.enter="searchFriends"
              placeholder="친구 id 입력"
            />
            <button class="search-btn" @click.prevent="searchFriends">
              검색
            </button>
          </div>
          <div class="searched-friend-list">
            <br />
            <ul class="searched-friend-list-ul">
              <li
                class="search-friend-data"
                v-for="friend in searchResult"
                :key="friend.userId"
              >
                <div class="search-friend-id">{{ friend.userId }}</div>
                <div class="search-friend-name">{{ friend.userName }}</div>
                <button
                  class="search-friend-btn"
                  @click.prevent="followUser(friend.userId)"
                >
                  팔로우
                </button>
              </li>
            </ul>
          </div>
        </div>
      </div>
      <input class="withdrawal-btn" value="회원탈퇴" @click="removeUser" />
    </main>

    <!-- 푸터 들어갈 곳 -->
    <TheFooter></TheFooter>
  </div>
</template>

<script>
import HeaderNav from "@/components/common/HeaderNav.vue";
import TheFooter from "@/components/common/TheFooter.vue";
import UserInfo from "@/components/MyPage/UserInfo.vue";
import UserInfoEdit from "../components/MyPage/UserInfoEdit.vue";

export default {
  name: "MyPageView",

  data() {
    return {
      imgFile: null,
      searchWord: "",
    };
  },
  computed: {
    user() {
      return this.$store.state.hui.user;
    },
    isUpdating() {
      return this.$store.state.hui.isUpdating;
    },
    idols() {
      return this.$store.state.hui.idols;
    },
    friends() {
      return this.$store.state.hui.friends;
    },
    searchResult() {
      return this.$store.state.hui.searchFriends;
    },
    userImg() {
      return this.$store.state.hui.user.userImg;
    },
    url() {
      const timestamp = new Date().getTime();
      return `http://localhost:9999/user/img/${this.user.userId}?timestamp=${timestamp}`;
    },
  },
  components: {
    HeaderNav,
    TheFooter,
    UserInfo,
    UserInfoEdit,
  },

  methods: {
    deleteFriend(userId) {
      this.$store.dispatch("deleteFollowing", userId);
    },

    searchFriends() {
      if (this.searchWord.trim().length === 0) {
        alert("검색어를 입력해주세요");
        this.$refs.search.focus();
      } else {
        this.$store.dispatch("searchFriends", {
          userId: this.user.userId,
          searchWord: this.searchWord,
        });
        this.searchWord = "";
      }
    },
    followUser(friendId) {
      this.$store
        .dispatch("followUser", {
          userId: this.user.userId,
          friendId: friendId,
        })
        .then(() => {
          setTimeout(() => {
            this.$store.dispatch("getIdols", this.user.userId);
          }, 100);
        });
    },
    unfollow(friendId) {
      this.$store
        .dispatch("unfollowUser", {
          userId: this.user.userId,
          friendId: friendId,
        })
        .then(() => {
          setTimeout(() => {
            this.$store.dispatch("getIdols", this.user.userId);
          }, 100);
        });
    },
    removeUser() {
      if (!confirm("정말 탈퇴하실건가요?")) return;

      const userPass = prompt("비밀번호를 입력해주세요");

      this.$store
        .dispatch("removeUser", {
          userId: this.user.userId,
          userPass: userPass,
        })
        .then(() => {
          alert("탈퇴가 완료되었습니다.");
          this.$router.push("/main");
        });
    },
  },

  created() {
    this.$store.dispatch("getFans", this.user.userId);
    this.$store.dispatch("getIdols", this.user.userId);
    this.$store.commit("SET_IS_UPDATING", true);
  },

  destroyed() {
    this.$store.commit("SET_SEARCH_FRIENDS", []);
  },
};
</script>

<style scoped>
h2 {
  text-align: center;
}

.search-container {
  display: flex;
  justify-content: space-around;
}

.search-friend {
  border: 1px solid #ffd4d4;
  border-radius: 7px;
  height: 1.2rem;
  width: 14rem;
  padding: 0.3rem;
}

.searched-friend-list-ul {
  height: 13.5rem;
  overflow-y: auto;
}

.searched-friend-list-ul::-webkit-scrollbar {
  width: 0.4rem;
  /* 스크롤 바의 너비 */
}

.searched-friend-list-ul::-webkit-scrollbar-track {
  background-color: transparent;
  /* 스크롤 바 트랙의 배경색 */
}

.searched-friend-list-ul::-webkit-scrollbar-thumb {
  background-color: #d4d4d4;
  /* 스크롤 바 썸의 색상 */
  border-radius: 5px;
  /* 스크롤 바 썸의 모서리 반경 */
}

.searched-friend-list-ul::-webkit-scrollbar-thumb:hover {
  background-color: #bbbbbb;
  /* 스크롤 바 썸의 호버 상태 색상 */
}

.search-friend-data {
  display: grid;
  grid-template-columns: repeat(5, 3.5rem);
  gap: 0.6rem;
  justify-content: center;
  text-align: center;
}

.search-friend-id {
  grid-column: 1 / span 2;
  padding: 0.2rem 0rem;
}

.search-friend-name {
  grid-column: 3 / span 2;
  padding: 0.2rem 0rem;
}

.search-friend-btn {
  border: 1px solid #aacb73;
  border-radius: 5px;
  background-color: #ffffff;
  color: #8fc040;
  font-weight: bold;
  padding: 0.2rem 0rem;
}
.search-friend-btn:hover {
  cursor: pointer;
  background-color: #8fc040;
  color: #ffffff;
}

.idol-list {
  display: grid;
  grid-template-columns: repeat(8, 2.5rem);
}

.idol-list-img {
  width: 100%;
}

.idol-list-id {
  padding-top: 0.5rem;
  text-align: center;
  font-weight: bold;
  grid-column: 2 / span 3;
}

.idol-list-name {
  padding-top: 0.5rem;
  text-align: center;
  font-weight: bold;
  grid-column: 5 / span 2;
}

.idol-list-btn {
  grid-column: 8 / span 1;
}

.idol-list-btn:hover {
  height: 2.5rem;
  border-radius: 8px;
  background-color: #f56969;
  color: #ffffff;
}

.speak-numbers {
  display: flex;
  justify-content: flex-end;
  margin-bottom: 1rem;
  color: #fc6b6b;
}

.profile-img {
  width: 100%;
  height: 345px;
  object-fit: cover;
  box-shadow: 1px 1px 7px rgb(165, 164, 164);
}

.edit-img {
  display: grid;
  justify-content: center;
  gap: 10px;
  background-color: #f2f2f2;
  padding: 1rem;
  border-radius: 10px;
  height: 25rem;
  box-shadow: 1px 1px 7px rgb(165, 164, 164);
}

.main {
  display: grid;
  grid-template-columns: repeat(1, 56rem);
  justify-content: center;
  margin: 8rem 0rem;
}

.user-info {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  grid-gap: 8rem;
}

.withdrawal-btn {
  text-align: center;
  color: white;
  background-color: #f56969;
  padding: 0.4rem 0.625rem;
  border-radius: 4px;
  border: #fa8080;
  cursor: pointer;
  margin-top: 1.5rem;
  width: 4rem;
  box-shadow: 0.7px 0.7px 3px rgb(165, 164, 164);
}

.withdrawal-btn:hover {
  cursor: pointer;
}

.withdrawal-btn:hover {
  background-color: #f52f2f;
}

.search-btn {
  text-align: center;
  color: white;
  background-color: #aacb73;
  padding: 0.4rem 0.625rem;
  border-radius: 4px;
  border: #aacb73;
  cursor: pointer;
  box-shadow: 0.7px 0.7px 3px rgb(165, 164, 164);
}

.search-btn:hover {
  background-color: #8ab644;
}

.friend-info {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  grid-gap: 8rem;
  margin-top: 4rem;
}

.friend-list,
.friend-search {
  background-color: #f2f2f2;
  flex-basis: 70%;
  padding: 1rem 2rem 2rem 2rem;
  border-radius: 10px;
  width: 20rem;
  box-shadow: 1px 1px 7px rgb(165, 164, 164);
}

.friend-list ul,
.friend-search ul {
  list-style: none;
  padding: 0;
  margin: 0;
}

.friend-list li,
.friend-search li {
  margin-bottom: 1rem;
}

.delete-btn {
  background: none;
  border: none;
  color: #e99292;
  font-weight: bold;
  cursor: pointer;
  padding: 0;
  float: right;
}

.search-container {
  display: flex;
  align-items: center;
}

.search-container {
  display: flex;
  align-items: center;
}

.search-container input {
  margin-right: 0.625rem;
  border: 1px solid white;
  border-radius: 7px;
  height: 1.2rem;
  box-shadow: 1px 1px 3px rgb(201, 200, 200);
}

.friend-list-area {
  height: 16rem;
  overflow-y: auto;
}

.friend-list-area::-webkit-scrollbar {
  width: 0.4rem;
  /* 스크롤 바의 너비 */
}

.friend-list-area::-webkit-scrollbar-track {
  background-color: transparent;
  /* 스크롤 바 트랙의 배경색 */
}

.friend-list-area::-webkit-scrollbar-thumb {
  background-color: #d4d4d4;
  /* 스크롤 바 썸의 색상 */
  border-radius: 5px;
  /* 스크롤 바 썸의 모서리 반경 */
}

.friend-list-area::-webkit-scrollbar-thumb:hover {
  background-color: #bbbbbb;
  /* 스크롤 바 썸의 호버 상태 색상 */
}
</style>
