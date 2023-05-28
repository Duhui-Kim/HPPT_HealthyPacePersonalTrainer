<template>
  <main>
    <!-- 날짜버튼 -->
    <section class="date-container">
      <div class="date-container-button left" @click="decrementDate()">
        <i class="fa-solid fa-caret-left"></i>
      </div>
      <div class="date-container-date">
        {{ year }}년 {{ month }}월 {{ day }}일
      </div>
      <div
        class="date-container-button rigth"
        v-if="flag"
        @click="incrementDate()"
      >
        <i class="fa-solid fa-caret-left fa-rotate-180"></i>
      </div>
    </section>

    <article>
      <!-- 아침 점심 저녁 들어갈 영역-->
      <div class="container">
        <!-- 아침 -->
        <div class="meal morning">
          <div class="meal-morning meal-title">아침</div>
          <!-- 아침 카드형식 -->
          <div class="meal-list">
            <!-- 반복 -->
            <div
              v-for="food in morning"
              :key="food.mealId"
              class="meal-list-content"
            >
              <img
                v-if="!food.mealImg"
                src="@/assets/음식사진 기본 이미지.jpg"
                class="meal-list-content-img"
              />
              <img
                v-else-if="food.mealImg"
                :src="'http://localhost:9999/mealPlan/img/' + food.mealId"
                class="meal-list-content-img"
                :key="food.mealImg"
              />
              <div class="meal-list-content-text">
                <div class="meal-list-content-text-name">
                  {{ food.menuName }}
                </div>
                <div class="nu" style="font-weight: 600">
                  {{ food.menuKcal }} Kcal
                </div>
                <div class="nu nutri">탄수: {{ food.carbo }} g</div>
                <div class="nu nutri">단백질: {{ food.protein }} g</div>
                <div class="nu nutri">지방: {{ food.fat }} g</div>
              </div>
              <!-- 삭제 버튼 -->
              <div
                class="delete-btn"
                @click="deleteMeal(food.mealId, 1, food.eatingTime)"
              >
                X
              </div>
            </div>
            <!-- 맨 끝에 추가하는 카드 -->
            <div class="meal-list-content add-meal" @click="addMeal(1)">+</div>
          </div>
        </div>

        <!-- 점심 -->
        <div class="meal lunch">
          <div class="meal-lunch meal-title">점심</div>
          <div class="meal-list">
            <!-- 반복 -->

            <div
              v-for="food in lunch"
              :key="food.mealId"
              class="meal-list-content"
            >
              <img
                v-if="!food.mealImg"
                src="@/assets/음식사진 기본 이미지.jpg"
                class="meal-list-content-img"
              />
              <img
                v-else-if="food.mealImg"
                :src="'http://localhost:9999/mealPlan/img/' + food.mealId"
                class="meal-list-content-img"
                :key="food.mealImg"
              />
              <div class="meal-list-content-text">
                <div class="meal-list-content-text-name">
                  {{ food.menuName }}
                </div>
                <div class="nu" style="font-weight: 600">
                  {{ food.menuKcal }} Kcal
                </div>
                <div class="nu nutri">탄수: {{ food.carbo }} g</div>
                <div class="nu nutri">단백질: {{ food.protein }} g</div>
                <div class="nu nutri">지방: {{ food.fat }} g</div>
              </div>
              <!-- 삭제 버튼 -->
              <div
                class="delete-btn"
                @click="deleteMeal(food.mealId, 2, food.eatingTime)"
              >
                X
              </div>
            </div>
            <!-- 맨 끝에 추가하는 카드 -->
            <div class="meal-list-content add-meal" @click="addMeal(2)">+</div>
          </div>
        </div>

        <!-- 저녁 -->
        <div class="meal dinner">
          <div class="meal-dinner meal-title">저녁</div>
          <div class="meal-list">
            <!-- 반복 -->
            <div
              v-for="food in dinner"
              :key="food.mealId"
              class="meal-list-content"
            >
              <img
                v-if="!food.mealImg"
                src="@/assets/음식사진 기본 이미지.jpg"
                class="meal-list-content-img"
              />
              <img
                v-else-if="food.mealImg"
                :src="'http://localhost:9999/mealPlan/img/' + food.mealId"
                class="meal-list-content-img"
                :key="food.mealImg"
              />
              <div class="meal-list-content-text">
                <div class="meal-list-content-text-name">
                  {{ food.menuName }}
                </div>
                <div class="nu" style="font-weight: 600">
                  {{ food.menuKcal }} Kcal
                </div>
                <div class="nu nutri">탄수: {{ food.carbo }} g</div>
                <div class="nu nutri">단백질: {{ food.protein }} g</div>
                <div class="nu nutri">지방: {{ food.fat }} g</div>
              </div>
              <!-- 삭제 버튼 -->
              <div
                class="delete-btn"
                @click="deleteMeal(food.mealId, 3, food.eatingTime)"
              >
                X
              </div>
            </div>
            <!-- 맨 끝에 추가하는 카드 -->
            <div class="meal-list-content add-meal" @click="addMeal(3)">+</div>
          </div>
        </div>
      </div>
    </article>

    <!-- 모달 -->
    <div class="modal-container" v-if="viewModal">
      <div class="modal-content">
        <input
          class="input a1"
          type="text"
          placeholder="음식이름으로 검색"
          v-model="searchMenuInput"
          v-on:keyup.enter="searchMenu"
        />
        <button class="btn b1" @click.prevent="searchMenu">검색</button>
        <div class="c1">
          <div class="search-result-title">검색결과</div>
          <!-- 검색결과 들어갈 자리 -->
          <div class="search-result-list">
            <!-- 결과 값 반복-->
            <div
              v-for="menu in searchedMenus"
              :key="menu.num"
              class="search-result"
            >
              <div class="search-result-name">
                {{ menu.menuName }}
              </div>
              <button class="search-result-btn" @click="selectMenu(menu)">
                선택
              </button>
            </div>
          </div>
        </div>

        <input
          class="input k1"
          type="text"
          placeholder="메뉴이름 직접 입력"
          v-model="menuName"
          :disabled="insertCheck"
        />
        <input
          class="input d1"
          type="number"
          placeholder="칼로리"
          v-model="menuKcal"
          :disabled="insertCheck"
        />
        <input
          class="input e1"
          type="number"
          placeholder="탄수화물"
          v-model="carbo"
          :disabled="insertCheck"
        />
        <input
          class="input f1"
          type="number"
          placeholder="단백질"
          v-model="protein"
          :disabled="insertCheck"
        />
        <input
          class="input g1"
          type="number"
          placeholder="지방"
          v-model="fat"
          :disabled="insertCheck"
        />
        <input
          class="input h1"
          type="number"
          placeholder="섭취량(g)"
          v-model="intake"
        />
        <div class="choose-file-btn">
          <label class="choose-file-label l1" for="profile-pic">
            사진 등록
          </label>
          <input
            type="file"
            id="profile-pic"
            accept="image/*"
            @change="uploadFile"
          />
        </div>
        <button class="btn i1 close-modal-btn" @click="closeModal">취소</button>
        <button class="btn j1" @click="registMeal">등록</button>
      </div>
    </div>
  </main>
</template>

<script>
export default {
  data() {
    return {
      //날짜 세팅
      year: null,
      month: null,
      day: null,
      //모달 열고 닫게 하는 boolean
      viewModal: false,
      //식사 등록 관련
      userId: null,
      menuName: "",
      intake: null,
      menuKcal: null,
      carbo: null,
      protein: null,
      fat: null,
      imgFile: null,
      divideTime: null,

      //db에서 mealplan 불러오기 관련
      mealList: [],

      //메뉴 이름으로 검색
      searchMenuInput: "",
      insertCheck: false,

      //날짜 버튼 관련
      flag: false,
    };
  },
  //스토에서 state 에 있는 데이터 가져오는거
  computed: {
    user() {
      return this.$store.state.hui.user;
    },
    morning() {
      return this.$store.state.hwi.morning;
    },
    lunch() {
      return this.$store.state.hwi.lunch;
    },
    dinner() {
      return this.$store.state.hwi.dinner;
    },
    searchedMenus() {
      return this.$store.state.hwi.searchedMenus;
    },
  },

  methods: {
    //모달창 열리고 닫히게 하는 메서드
    addMeal(num) {
      this.viewModal = true;
      this.divideTime = num;
    },

    closeModal() {
      this.viewModal = false;
      //메뉴검색창 초기화
      this.$store.commit("SET_SEARCHEDMENUS", []);
      this.searchMenuInput = "";
      this.menuName = "";
      this.intake = null;
      this.menuKcal = null;
      this.carbo = null;
      this.protein = null;
      this.fat = null;
      this.imgFile = null;
      this.insertCheck = false;
    },
    uploadFile(event) {
      const file = event.target.files[0];

      this.imgFile = file;
    },
    //식사 등록 하는 메서드
    registMeal() {
      if (
        this.menuName.trim().length === 0 ||
        !this.intake === null ||
        !this.menuKcal === null ||
        !this.carbo === null ||
        !this.protein === null ||
        !this.fat === null
      ) {
        alert("값을 모두 입력해주세요!");
        return;
      }

      let Meal = {
        userId: this.$store.state.hui.user.userId,
        menuName: this.menuName,
        menuKcal: this.menuKcal,
        carbo: this.carbo,
        protein: this.protein,
        fat: this.fat,
        intake: this.intake,
        eatingTime: this.year + "-" + this.month + "-" + this.day,
        divideTime: this.divideTime,
      };

      this.$store
        .dispatch("registMeal", Meal)
        .then(() => {
          // 파일 업로드 진행
          if (this.imgFile) {
            const formData = new FormData();
            formData.append("imgFile", this.imgFile);
            formData.append("mealId", this.$store.state.hwi.mealId);
            this.$store.dispatch("uploadMealImg", formData);
          }
        })
        .then(() => {
          //목록 업데이트
          setTimeout(() => {
            this.$store.dispatch("getUserMeal", {
              userId: Meal.userId,
              date: Meal.eatingTime,
            });
          }, 300);
          //메뉴검색창 초기화
          this.$store.commit("SET_SEARCHEDMENUS", []);
          this.searchMenuInput = "";
          this.menuName = "";
          this.intake = null;
          this.menuKcal = null;
          this.carbo = null;
          this.protein = null;
          this.fat = null;
          this.imgFile = null;

          alert("식사 등록이 완료되었습니다.");
          this.viewModal = false;
          this.insertCheck = false;
        });
    },
    //식사 삭제
    deleteMeal(mealId, num, eatingTime) {
      if (!confirm("정말로 삭제하시겠습니까?")) return;
      this.$store
        .dispatch("deleteMeal", {
          mealId: mealId,
          num: num,
        })
        .then(() => {
          //목록 업데이트
          setTimeout(() => {
            this.$store.dispatch("getUserMeal", {
              userId: this.user.userId,
              date: eatingTime,
            });
          }, 300);
          alert("등록한 식사가 삭제되었습니다.");
        });
    },

    //메뉴 이름 검색
    searchMenu() {
      this.$store.dispatch("searchMenu", this.searchMenuInput);
    },

    //메뉴 선택
    selectMenu(menu) {
      this.menuName = menu.menuName;
      this.intake = menu.intake;
      this.menuKcal = menu.menuKcal;
      this.carbo = menu.carbo;
      this.protein = menu.protein;
      this.fat = menu.fat;
      this.insertCheck = true;
    },

    //날짜 변경
    decrementDate() {
      const today = new Date();

      const date = new Date(this.year, this.month - 1, this.day);
      date.setDate(date.getDate() - 1);

      this.year = date.getFullYear();
      this.month = date.getMonth() + 1;
      this.day = date.getDate();

      //오른쪽 버튼 표시하기 위한 flag 설정
      if (date.toDateString() !== today.toDateString()) {
        this.flag = true;
      }
      const pdate = `${this.year}-${this.month}-${this.day}`;

      this.$store.dispatch("getUserMeal", {
        userId: this.user.userId,
        date: pdate,
      });
    },

    incrementDate() {
      const today = new Date();

      const date = new Date(this.year, this.month - 1, this.day);
      date.setDate(date.getDate() + 1);

      this.year = date.getFullYear();
      this.month = date.getMonth() + 1;
      this.day = date.getDate();

      //오른쪽 버튼 표시하기 위한 flag 설정
      if (date.toDateString() === today.toDateString()) {
        this.flag = false;
      }

      const pdate = `${this.year}-${this.month}-${this.day}`;

      this.$store.dispatch("getUserMeal", {
        userId: this.user.userId,
        date: pdate,
      });
    },
  },

  //페이지 로드되면 오늘 날짜 자동으로 출력되게
  mounted() {
    const selectedDate = new Date();
    this.year = selectedDate.getFullYear();
    this.month = selectedDate.getMonth() + 1;
    this.day = selectedDate.getDate();

    const userId = this.user.userId;
    const date = `${this.year}-${this.month}-${this.day}`;

    this.$store.dispatch("getUserMeal", {
      userId: userId,
      date: date,
    });
  },
};
</script>

<style scoped>
.date-container {
  display: grid;
  grid-template-columns: repeat(8, 7rem);
  grid-template-rows: repeat(1, 3rem);
  justify-content: center;
  align-items: center;
  grid-gap: 3rem;
  margin: 4rem 1rem 3rem 1rem;
}

.date-container-date {
  border-radius: 20px;
  text-align: center;
  padding: 0.6rem;
  height: 3rem;
  font-size: xx-large;
  font-weight: bold;
  grid-column: 4 / span 2;
  background-color: #ecece0;
  box-shadow: 0 2px 4px rgba(134, 134, 134, 1);
}

.date-container-button {
  border-radius: 15px;
  text-align: center;
  font-size: xx-large;
  color: #41412f6c;
  background-color: #ffffff;
  font-weight: bold;
  border: 2px solid #41412f6c;
  opacity: 0.7;
  width: 3rem;
  margin: 0 auto;
  box-shadow: 0 1px 3px rgba(134, 134, 134, 1);
}

.nu {
  white-space: nowrap;
  overflow-x: auto;
}

.nu::-webkit-scrollbar {
  height: 0.2rem; /* 스크롤 바의 너비 */
}

.nu::-webkit-scrollbar-track {
  background-color: transparent; /* 스크롤 바 트랙의 배경색 */
}

.nu::-webkit-scrollbar-thumb {
  background-color: #888; /* 스크롤 바 썸의 색상 */
  border-radius: 5px; /* 스크롤 바 썸의 모서리 반경 */
}

.nu::-webkit-scrollbar-thumb:hover {
  background-color: #555; /* 스크롤 바 썸의 호버 상태 색상 */
}

.date-container-button:hover {
  cursor: pointer;
  background-color: #ffd4d4;
  color: #ffffff;
  border-color: #ffd4d4;
}

.left {
  grid-column: 3 / span 1;
}

.rigth {
  grid-column: 6 / span 1;
}

.container {
  display: grid;
  grid-template-columns: repeat(3, 30rem);
  gap: 5rem;
  justify-content: center;
  margin-bottom: 5rem;
}

.meal {
  border-radius: 10px;
  width: 32rem;
  height: 40rem;
  transition: 0.4s ease;
  box-shadow: 1px 2px 6px rgba(134, 134, 134, 1);
  display: grid;
  grid-template-rows: repeat(2, 3rem);
  gap: 1rem;
  opacity: 0.7;
  padding: 1rem;
  background-color: #f8f8eafd;
}

.delete-btn {
  position: absolute;
  top: 0.6rem;
  right: 0.6rem;
  font-weight: bolder;
  cursor: pointer;
  visibility: hidden;
  transition: visibility 0s linear 0.3s, opacity 0.3s linear;
  color: #6e6d0f;
  font-size: 20px;
}

.meal-title {
  border-radius: 30px;
  margin-bottom: 1rem;
  width: 5rem;
  height: 2.6rem;
  padding-top: 0.5rem;
  text-align: center;
  font-size: x-large;
  box-shadow: 2px 1px 4px rgba(134, 134, 134, 1);
}

.meal-list {
  grid-row: 2 / span 10;
  display: grid;
  justify-content: center;
  grid-template-columns: repeat(2, 15rem);
  grid-template-rows: auto;
  gap: 0.8rem;
  overflow-y: auto;
}

.meal-list::-webkit-scrollbar {
  width: 0.5rem; /* 스크롤 바의 너비 */
}

.meal-list::-webkit-scrollbar-track {
  background-color: transparent; /* 스크롤 바 트랙의 배경색 */
}

.meal-list::-webkit-scrollbar-thumb {
  background-color: #888; /* 스크롤 바 썸의 색상 */
  border-radius: 5px; /* 스크롤 바 썸의 모서리 반경 */
}

.meal-list::-webkit-scrollbar-thumb:hover {
  background-color: #555; /* 스크롤 바 썸의 호버 상태 색상 */
}

.meal-list-content {
  display: grid;
  grid-template-rows: repeat(6, 4rem);
  border: 2px solid rgb(187, 187, 181);
  width: 14rem;
  height: 21rem;
  border-radius: 10px;
  transition: border 0.4s ease;
  margin-top: 1rem;
  position: relative;
  transition: visibility 0s, opacity 0.3s ease;
}

.meal-list-content:hover {
  /* cursor: pointer; */
  border: 2px solid #6e6d0f;
}

.meal-list-content:hover .delete-btn {
  visibility: visible;
  opacity: 1;
  transition-delay: 0.1s;
}

.meal-list-content-img {
  grid-row: 1 / span 4;
  border-radius: 8px;
  width: 224px;
  height: 100%;
  object-fit: cover;
}

.meal-list-content-text {
  grid-row: 5 / span 2;
  display: grid;
  justify-content: center;
  grid-template-columns: repeat(2, 7rem);
  grid-template-rows: repeat(3, 1.5rem);
  text-align: center;
  gap: 0.2rem;
}

.nutri {
  font-size: 13px;
}

.meal-list-content-text > * {
  padding: 0rem 0.5rem;
}

.meal-list-content-text-name {
  font-weight: bolder;
  font-size: 1.2rem;
  grid-column: 1 / span 2;

  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.meal-morning {
  background-color: #f6e4a3;
}

.meal-lunch {
  background-color: #c2f6a3;
}

.meal-dinner {
  background-color: #ffd4d4;
}

.add-meal {
  display: block;
  height: 186px;
  padding-top: 150px;
  text-align: center;
  font-size: xx-large;
  font-weight: bolder;
}

.add-meal:hover {
  border: 2px solid #aacb73;
  color: #aacb73;
  cursor: pointer;
}

/*** 
      
      모달 관련 CSS 
      
      ***/

/* 버튼 스타일 */
.open-modal-btn {
  background-color: #f1f1f1;
  color: #333;
  padding: 0.5rem 1rem;
  border: none;
  cursor: pointer;
}

/* 모달 컨테이너 */
.modal-container {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;

  transition: visibility 0s, opacity 0.3s;
  z-index: 9999;
}

/* 모달 내용 */
.modal-content {
  background-color: #ffd4d4;
  padding: 2rem;
  border-radius: 5px;
  width: 50rem;
  height: 30rem;
  display: grid;
  grid-template-columns: repeat(6, 1fr);
  grid-template-rows: repeat(5, 1fr);
  gap: 1rem;
  box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.527);
}

.modal-content > * {
  box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
  border: 3px solid rgb(255, 255, 255);
}

.input {
  margin: 1rem;
  border-radius: 10px;
  padding: 0.5rem;
  font-size: medium;
}

.btn {
  width: 7rem;
  height: 3rem;
  border-radius: 10px;
  border: 2px;
}

.btn:hover {
  cursor: pointer;
}

.a1 {
  grid-column: 1 / span 4;
  grid-row: 1 / span 1;
}

.b1 {
  margin-top: 1.2rem;
  grid-column: 5 / span 1;
  grid-row: 1 / span 1;
}

.b1:hover {
  color: #ffffff;
  background-color: #6e6e6e;
}

.c1 {
  grid-column: 1 / span 2;
  grid-row: 2 / span 4;
  height: 22rem;
  display: grid;
  border-radius: 15px;
  grid-template-rows: repeat(7, 3rem);
  gap: 0.2rem;
}
.k1 {
  grid-column: 3 / span 2;
  grid-row: 2 / span 1;
}
.d1 {
  grid-column: 3 / span 2;
  grid-row: 3 / span 1;
}

.e1 {
  grid-column: 5 / span 2;
  grid-row: 3 / span 1;
}

.f1 {
  grid-column: 3 / span 2;
  grid-row: 4 / span 1;
}

.g1 {
  grid-column: 5 / span 2;
  grid-row: 4 / span 1;
}

.h1 {
  grid-column: 5 / span 2;
  grid-row: 2 / span 1;
}

.i1 {
  grid-column: 4 / span 1;
  grid-row: 5 / span 1;
}

.i1:hover {
  color: #ffffff;
  background-color: #6e6e6e;
}

.j1 {
  grid-column: 5 / span 1;
  grid-row: 5 / span 1;
  background-color: #aacb73;
}

.j1:hover {
  color: #ffffff;
  background-color: #6eb300;
}

.choose-file-btn {
  display: flex;
  justify-content: center;
  align-items: center;
  border: none;
  box-shadow: none;
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

  box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
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

/* 모달 보이기 .modal-container.active {
  visibility: visible;
  opacity: 1;
} */

.search-result {
  display: grid;
  grid-template-columns: repeat(6, 2rem);
  gap: 0.4rem;
  background-color: #ffffff;
  border-radius: 10px;
  height: 2rem;
  margin: 0.4rem 0.2rem 0.4rem 0.2rem;
}

.search-result > * {
  margin-top: 0.2rem;
}

.search-result-title {
  background-color: #ffffff;
  padding-top: 0.7rem;
  text-align: center;
  font-size: large;
  font-weight: bold;
}

.search-result-list {
  overflow-y: auto;
  grid-row: 2 / span 6;
  height: 18.6rem;
}

.search-result-list::-webkit-scrollbar {
  width: 0rem;
  height: 0rem;
}

.search-result-name {
  grid-column: 1 / span 5;
  padding-left: 0.5rem;
  white-space: nowrap;
  overflow-x: auto;
}

.search-result-name::-webkit-scrollbar {
  width: 0rem;
  height: 0rem;
}

.search-result-btn {
  background-color: #ffffff;
  border: 0px;
  width: 3rem;
  height: 1.5rem;
  color: #aacb73;
  border: 1px solid #aacb73;
  border-radius: 5px;
  transition: background-color 0.6s ease;
  transition: color 0.6s ease;
}

.search-result-btn:hover {
  height: 1.5rem;
  background-color: #aacb73;
  border-radius: 5px;
  color: #ffffff;
}
</style>
