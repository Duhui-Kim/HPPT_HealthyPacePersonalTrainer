<template>
  <main>
    <div class="profile">
      <div class="profile-info">
        <!-- 유저 프로필 사진 -->
        <div class="profile">
          <img
            v-if="!user.userImg"
            class="profile-img"
            src="@/assets/프로필 기본 이미지.png"
            alt="User Profile"
          />
          <img
            v-else-if="user.userImg"
            class="profile-img"
            :src="url"
            :key="user.userImg"
            alt="프로필 사진"
          />
        </div>

        <!--  Goals -->
        <div class="goals-section">
          <div class="goals-header">Goals</div>
          <div class="goals-content">
            <p v-cloak>목표 감량 체중 : {{ targetKg }} kg</p>
            <p v-cloak>현재 감량 체중 : {{ reduceKg }} kg</p>
            <p v-cloak ref="pro">진행도 : {{ pro }} %</p>
            <div class="bar-graph">
              <div class="bar" ref="progressBar"></div>
            </div>
          </div>
        </div>

        <!-- Ranking -->
        <div class="ranking-section">
          <div class="ranking-header">Ranking</div>
          <div class="ranking-content">
            <p v-cloak class="show-rank">현재 랭킹 : {{ ranking }}등</p>
            <hr />
            <br />
            <div class="rank-area">
              <div
                class="ranking-list"
                v-for="(rank, index) in rankingList"
                :key="index"
              >
                <p
                  v-cloak
                  class="ranking-idx"
                  :class="{ 'bold-text': rank.check }"
                >
                  {{ index + 1 }}등
                </p>
                <p
                  v-cloak
                  class="ranking-name"
                  :class="{ 'bold-text': rank.check }"
                >
                  {{ rank.userName }}
                </p>
                <p
                  v-cloak
                  class="ranking-progress"
                  :class="{ 'bold-text': rank.check }"
                >
                  {{ rank.progress.toFixed(1) }}% 달성
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <br />
    <br />
    <br />
    <!-- 영양정보 -->
    <div class="nutrition">
      <div class="nutrition-info">
        <div>
          <div class="chart">
            <canvas ref="KcalChart"></canvas>
            <div class="chart-label" v-cloak>{{ Kcal[0] }}%</div>
            <div class="chart-title">Kcal</div>
            <div class="todayKcal">
              <div>오늘의 칼로리</div>
              <span v-cloak class="ttoday">{{ TotalKcal.toFixed(2) }} Kcal&nbsp;</span>
              <span v-cloak class="today">/ {{ BaseKcal }} Kcal</span>
            </div>
          </div>
        </div>

        <div>
          <div class="chart">
            <canvas ref="CarboChart"></canvas>
            <div class="chart-label" v-cloak>{{ Carbo[0] }}%</div>
            <div class="chart-title">Carbo</div>
          </div>
        </div>

        <div>
          <div class="chart">
            <canvas ref="ProteinChart"></canvas>
            <div class="chart-label" v-cloak>{{ Protein[0] }}%</div>
            <div class="chart-title">Protein</div>
          </div>
        </div>

        <div>
          <div class="chart">
            <canvas ref="FatChart"></canvas>
            <div class="chart-label" v-cloak>{{ Fat[0] }}%</div>
            <div class="chart-title">Fat</div>
          </div>
        </div>
      </div>
    </div>
  </main>
</template>

<script>
export default {
  name: "AfterLogin",

  data() {
    return {
      pro: 0,
    };
  },

  computed: {
    user() {
      return this.$store.state.hui.user;
    },
    Kcal() {
      return this.$store.state.hwi.Kcal;
    },
    Carbo() {
      return this.$store.state.hwi.Carbo;
    },
    Protein() {
      return this.$store.state.hwi.Protein;
    },
    Fat() {
      return this.$store.state.hwi.Fat;
    },
    BaseKcal() {
      return this.$store.state.hwi.BaseKcal;
    },
    TotalKcal() {
      return this.$store.state.hwi.TotalKcal;
    },
    url() {
      const timestamp = new Date().getTime();
      return `http://localhost:9999/user/img/${this.user.userId}?timestamp=${timestamp}`;
    },
    targetKg() {
      return parseFloat((this.user.startKcal / 7000).toFixed(2));
    },
    remainKg() {
      return parseFloat((this.user.remainKcal / 7000).toFixed(2));
    },
    reduceKg() {
      return (this.targetKg - this.remainKg).toFixed(2);
    },
    progress() {
      return ((this.reduceKg / this.targetKg) * 100).toFixed(2);
    },
    idols() {
      let list = JSON.parse(JSON.stringify(this.$store.state.hui.idols));

      for (let i = 0; i < list.length; i++) {
        list[i].progress =
          ((list[i].startKcal - list[i].remainKcal) / list[i].startKcal) * 100;
      }

      let sortedList = list.sort((a, b) => {
        if (a.progress > b.progress) return -1;
        if (a.progress < b.progress) return 1;
        return 0;
      });

      return sortedList;
    },

    rankingList() {
      let list = JSON.parse(JSON.stringify(this.$store.state.hui.idols));
      let user = JSON.parse(JSON.stringify(this.user));
      user.userName = "본인";
      user.check = true;
      list.push(user);

      for (let i = 0; i < list.length; i++) {
        list[i].progress =
          ((list[i].startKcal - list[i].remainKcal) / list[i].startKcal) * 100;
      }

      let sortedList = list.sort((a, b) => {
        if (a.progress > b.progress) return -1;
        if (a.progress < b.progress) return 1;
        return 0;
      });
      return sortedList;
    },
    ranking() {
      let list = this.idols;
      let cnt = 1;
      for (let i = 0; i < list.length; i++) {
        if (parseFloat(this.progress) < list[i].progress) {
          cnt += 1;
        }
      }
      return cnt;
    },
  },

  created() {
    this.initailize();
    // 유저의 남은 칼로리 세팅
    this.$store.dispatch("getRemainKcal", this.user.userId);
    this.$store.dispatch("getIdols", this.user.userId);
  },
  mounted() {
    setTimeout(() => {
      this.drawKcalChart(this.Kcal);
      this.drawCarboChart(this.Carbo);
      this.drawProteinChart(this.Protein);
      this.drawFatChart(this.Fat);

      // barWidth를 서서히 증가시키는 로직
      const progressBar = this.$refs.progressBar;

      const targetWidth = parseFloat(this.progress);
      let currentWidth = 0;
      progressBar.style.width = `${currentWidth}%`;

      const increaseWidth = () => {
        if (currentWidth < targetWidth) {
          currentWidth += targetWidth / 100;
          progressBar.style.width = `${currentWidth}%`;
          this.pro = currentWidth.toFixed(2);

          setTimeout(increaseWidth, 10);
        } else {
          currentWidth = targetWidth;
        }
      };

      increaseWidth();
    }, 700);
  },
  methods: {
    initailize() {
      const selectedDate = new Date();
      this.year = selectedDate.getFullYear();
      this.month = selectedDate.getMonth() + 1;
      this.day = selectedDate.getDate();

      const date = `${this.year}-${this.month}-${this.day}`;

      // 유저가 먹은 데이터 가공
      this.$store
        .dispatch("getUserMeal", {
          userId: this.user.userId,
          date: date,
        })
        .then(() => {
          setTimeout(() => {
            this.$store.dispatch("updateNutri");
          }, 300);
        });
    },

    drawKcalChart(KcalPercent) {
      const canvas = this.$refs.KcalChart;
      const context = canvas.getContext("2d");

      const centerX = canvas.width / 2;
      const centerY = canvas.height / 2;
      const radius = Math.min(centerX, centerY) * 0.8;
      const startAngle = 0;
      const data = this.Kcal; // 도넛 차트에 표시할 데이터 (예: 30%, 70%)

      let currentAngle = startAngle;

      // 도넛 차트 그리기
      data.forEach((percentage) => {
        const sliceAngle = (percentage / 100) * Math.PI * 2;

        context.beginPath();
        context.arc(
          centerX,
          centerY,
          radius,
          currentAngle,
          currentAngle + sliceAngle
        );
        context.lineWidth = radius * 0.5;
        context.shadowOffsetX = 2;
        context.shadowOffsetY = 2;
        context.shadowBlur = 5;
        context.shadowColor = "rgba(0, 0, 0, 0.5)";

        if (KcalPercent > 110) {
          context.strokeStyle = "#fc7f7f"; // 도넛 차트의 색상 설정
        } else if (KcalPercent < 75) {
          context.strokeStyle = "rgba(243, 233, 99, 0.911)";
        } else {
          context.strokeStyle = "rgba(59, 182, 75, 0.712)";
        }

        context.stroke();

        currentAngle += sliceAngle;
      });
    },
    drawCarboChart(CarboPercent) {
      const canvas = this.$refs.CarboChart;
      const context = canvas.getContext("2d");

      const centerX = canvas.width / 2;
      const centerY = canvas.height / 2;
      const radius = Math.min(centerX, centerY) * 0.8;
      const startAngle = 0;
      const data = this.Carbo; // 도넛 차트에 표시할 데이터 (예: 30%, 70%)

      let currentAngle = startAngle;

      // 도넛 차트 그리기
      data.forEach((percentage) => {
        const sliceAngle = (percentage / 100) * Math.PI * 2;

        context.beginPath();
        context.arc(
          centerX,
          centerY,
          radius,
          currentAngle,
          currentAngle + sliceAngle
        );
        context.lineWidth = radius * 0.5;
        context.shadowOffsetX = 2;
        context.shadowOffsetY = 2;
        context.shadowBlur = 5;
        context.shadowColor = "rgba(0, 0, 0, 0.5)";

        if (CarboPercent > 110) {
          context.strokeStyle = "#fc7f7f"; // 도넛 차트의 색상 설정
        } else if (CarboPercent < 75) {
          context.strokeStyle = "rgba(243, 233, 99, 0.911)";
        } else {
          context.strokeStyle = "rgba(59, 182, 75, 0.712)";
        }

        context.stroke();

        currentAngle += sliceAngle;
      });
    },
    drawProteinChart(ProteinPercent) {
      const canvas = this.$refs.ProteinChart;
      const context = canvas.getContext("2d");

      const centerX = canvas.width / 2;
      const centerY = canvas.height / 2;
      const radius = Math.min(centerX, centerY) * 0.8;
      const startAngle = 0;
      const data = this.Protein; // 도넛 차트에 표시할 데이터 (예: 30%, 70%)

      let currentAngle = startAngle;
      context.shadowOffsetX = 2;
      context.shadowOffsetY = 2;
      context.shadowBlur = 5;
      context.shadowColor = "rgba(0, 0, 0, 0.5)";

      // 도넛 차트 그리기
      data.forEach((percentage) => {
        const sliceAngle = (percentage / 100) * Math.PI * 2;

        context.beginPath();
        context.arc(
          centerX,
          centerY,
          radius,
          currentAngle,
          currentAngle + sliceAngle
        );
        context.lineWidth = radius * 0.5;

        if (ProteinPercent > 110) {
          context.strokeStyle = "#fc7f7f"; // 도넛 차트의 색상 설정
        } else if (ProteinPercent < 75) {
          context.strokeStyle = "rgba(243, 233, 99, 0.911)";
        } else {
          context.strokeStyle = "rgba(59, 182, 75, 0.712)";
        }

        context.stroke();

        currentAngle += sliceAngle;
      });
    },
    drawFatChart(FatPercent) {
      const canvas = this.$refs.FatChart;
      const context = canvas.getContext("2d");

      const centerX = canvas.width / 2;
      const centerY = canvas.height / 2;
      const radius = Math.min(centerX, centerY) * 0.8;
      const startAngle = 0;
      const data = this.Fat; // 도넛 차트에 표시할 데이터 (예: 30%, 70%)

      let currentAngle = startAngle;
      context.shadowOffsetX = 2;
      context.shadowOffsetY = 2;
      context.shadowBlur = 5;
      context.shadowColor = "rgba(0, 0, 0, 0.5)";

      // 도넛 차트 그리기
      data.forEach((percentage) => {
        const sliceAngle = (percentage / 100) * Math.PI * 2;

        context.beginPath();
        context.arc(
          centerX,
          centerY,
          radius,
          currentAngle,
          currentAngle + sliceAngle
        );
        context.lineWidth = radius * 0.5;

        if (FatPercent > 110) {
          context.strokeStyle = "#fc7f7f";
        } else if (FatPercent < 75) {
          context.strokeStyle = "rgba(243, 233, 99, 0.911)";
        } else {
          context.strokeStyle = "rgba(59, 182, 75, 0.712)";
        }

        context.stroke();

        currentAngle += sliceAngle;
      });
    },
  },
};
</script>

<style scoped>
body {
  margin: 0;
}

.rank-area {
  height: 10rem;
  overflow-x: hidden;
  overflow-y: auto;
  /* border: 1px solid rgb(190, <hr>, 190); */
  border-radius: 10px;
}

.rank-area::-webkit-scrollbar {
  width: 0.5rem; /* 스크롤 바의 너비 */
}

.rank-area::-webkit-scrollbar-track {
  background-color: transparent; /* 스크롤 바 트랙의 배경색 */
}

.rank-area::-webkit-scrollbar-thumb {
  background-color: #c0c0c0; /* 스크롤 바 썸의 색상 */
  border-radius: 5px; /* 스크롤 바 썸의 모서리 반경 */
}

.rank-area::-webkit-scrollbar-thumb:hover {
  background-color: #a7a7a7; /* 스크롤 바 썸의 호버 상태 색상 */
}
.show-rank {
  text-align: center;
  font-weight: bolder;
  font-size: larger;
  padding: 0;
  margin: 0;
}
.ranking-list {
  display: grid;
  grid-template-columns: repeat(5, 3.6rem);
  text-align: center;
}

.ranking-name {
  grid-column: 2 / span 2;
}

.ranking-progress {
  grid-column: 4 / span 2;
}
.bold-text {
  font-weight: bold;
}

.ttoday {
  color: #77ad5f;
}
main {
  margin: 6rem 0rem;
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 7rem;
  margin-bottom: 3rem;
}
.profile-img {
  width: 19rem;
  height: 19rem;
  object-fit: cover;
  border-radius: 20px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
}

.chart {
  position: relative;
}

.chart-label {
  position: absolute;
  top: 4.6rem;
  left: 9.4rem;
  transform: translate(-50%, -50%);
  font-size: 20px;
  font-weight: bold;
}

.chart-title {
  position: absolute;
  top: 1rem;
  left: 3rem;
  text-align: center;
  transform: translate(-50%, -50%);
  font-size: 24px;
  font-weight: bold;
  background-color: #ffffff;
  width: 6rem;
  padding: 0.3rem;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(139, 139, 139, 0.2);
}
.today {
  color: rgb(117, 117, 117);
}

.todayKcal {
  position: absolute;
  top: -6rem;
  left: 4.5rem;
  width: 16rem;
  text-align: center;
  padding: 0.3rem;
  border-radius: 6px;
  transform: translate(-50%, -50%);
  font-size: 18px;
  font-weight: bold;
  background-color: #ffffff;
  border: 2px solid #616161;
  box-shadow: 0 0 10px rgba(139, 139, 139, 0.2);
}

header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1.25rem;
  border: 1px solid black;
  background-color: #ffffff;
}

.profile {
  display: flex;
  justify-content: center;
  align-items: center;
  grid-column: 1 / span 2;
  width: 20rem;
  height: 20rem;
  margin: auto;
}

.profile-info {
  display: grid;
  grid-template-columns: repeat(6, 10rem);
  grid-gap: 5rem;
  /* align-items: center; */
}

.goals-section {
  background-color: #f2f2f2;
  padding: 20px;
  border-radius: 15px;
  padding: 2rem;
  grid-column: 2 / span 2;
  overflow: hidden;
  box-shadow: 0 0 10px rgba(22, 22, 22, 0.2);
  grid-column: 3 / span 2;
}

.goals-header {
  color: #333;
  font-size: 2.5rem;
  font-weight: bold;
  margin-bottom: 10px;
}

.goals-content {
  background-color: #ffffff;
  border: 1px solid #ccc;
  padding: 10px;
  border-radius: 5px;
  box-shadow: 0 0 10px rgba(56, 56, 56, 0.2);
  display: flex;
  flex-direction: column;
  justify-content: space-evenly;
}

.bar-graph {
  width: 100%;
  height: 20px;
  background-color: #f2f2f2;
  border-radius: 5px;
  overflow: hidden;
}

.bar {
  height: 100%;
  background-color: #ffd4d4;
  transition: width 1s ease;
}

.ranking-section {
  background-color: #f2f2f2;
  padding: 20px;
  border-radius: 15px;
  padding-top: 0rem;
  padding: 2rem;
  grid-column: 5 / span 2;
  box-shadow: 0 0 10px rgba(22, 22, 22, 0.2);
}

.ranking-header {
  color: #333;
  font-size: 2.5rem;
  font-weight: bold;
  margin-bottom: 10px;
}

.ranking-content {
  background-color: #ffffff;
  border: 1px solid #ccc;
  padding: 10px;
  border-radius: 5px;
  box-shadow: 0 0 10px rgba(56, 56, 56, 0.2);
}

.goals-content,
.ranking-content {
  height: 60%;
  padding: 2rem 1rem;
}

.chart {
  width: 200px;
  height: 200px;
}

.nutrition-info {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  grid-gap: 10rem;
}

.nutrition {
  display: flex;
  justify-content: center;
  background-color: #f2f2f2;
  border-radius: 15px;
  width: 88rem;
  height: 13rem;
  padding-top: 4rem;
  margin-top: 4rem;
  box-shadow: 0 0 10px rgba(22, 22, 22, 0.2);
}
</style>
