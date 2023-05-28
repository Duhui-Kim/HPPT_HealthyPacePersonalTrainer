<template>
  <div class="main">
    <!-- 메인영역 -->
    <main>
      <!--메인 이미지 캐러셀-->
      <section class="slider">
        <div class="carousel-wrapper">
          <div class="carousel">
            <div class="carousel-inner">
              <img
                src="@/assets/healthyImg7.jpg"
                alt="이미지 1"
                class="carousel-item"
              />
              <img
                src="@/assets/healthyImg8.jpg"
                alt="이미지 2"
                class="carousel-item"
              />
              <img
                src="@/assets/personaltrainerImg2.jpg"
                alt="이미지 3"
                class="carousel-item"
              />
              <img
                src="@/assets/healthyImg7.jpg"
                alt="이미지 1"
                class="carousel-item"
              />
            </div>
          </div>
        </div>
      </section>

      <br />
      <hr class="divide-line" />
      <br />

      <!-- 유튜브 랜덤 영상 세개 출력 -->
      <section>
        <div class="video-container-wrapper">
          <div class="video-container">
            <div v-for="video in videos" :key="video.videoId" class="video">
              <iframe
                class="video-iframe"
                :src="`https://www.youtube.com/embed/${video.videoId}`"
                :key="video"
                width="560"
                height="500"
                frameborder="0"
                allowfullscreen
              ></iframe>
            </div>
          </div>
        </div>
      </section>
    </main>
  </div>
</template>

<script>
export default {
  name: "BeforeLogin",
  methods: {
    searchVideosForMain(query) {
      this.$store.dispatch("searchVideosForMain", {
        query: query,
        maxResults: 3,
      });
    },
  },
  mounted() {
    this.searchVideosForMain("운동");
  },
  computed: {
    videos() {
      return this.$store.state.hwi.videos;
    },
  },
};
</script>

<style scoped>
.main {
  display: flex;
  justify-content: center;
  overflow-x: auto;
}
.carousel {
  /* width: 100%; */
  overflow: hidden;
  height: 25rem;
  max-width: 94%;
  margin-left: 3%;
  margin-top: 2rem;
}
.carousel-wrapper {
  min-width: 100%;

  width: 118.8rem;
  /* height: 3rem; */
}

.carousel-inner {
  display: flex;
  transition: transform 0.1s ease;
  animation: carousel-slide 15s infinite;
}

.carousel-item {
  flex-shrink: 0;
  width: 100%;
}

/* 캐러셀 슬라이드 애니메이션 */
@keyframes carousel-slide {
  0% {
    transform: translateX(0);
  }
  23.3% {
    transform: translateX(-100%);
  }
  33.3% {
    transform: translateX(-100%);
  }
  56.6% {
    transform: translateX(-200%);
  }
  66.6% {
    transform: translateX(-200%);
  }
  90% {
    transform: translateX(-300%);
  }
  100% {
    transform: translateX(-300%);
  }
}

/* 유튜브 영상 */
.video-container {
  display: flex;
  justify-content: space-around;
  align-items: center;
  padding: 1rem 2rem 3rem 2rem;
  margin: 0rem 4rem;
  /* min-width: 100%; */
}

.main::-webkit-scrollbar {
  width: 0.5rem; /* 스크롤 바의 너비 */
}

.main::-webkit-scrollbar-track {
  background-color: transparent; /* 스크롤 바 트랙의 배경색 */
}

.main::-webkit-scrollbar-thumb {
  background-color: #888; /* 스크롤 바 썸의 색상 */
  border-radius: 5px; /* 스크롤 바 썸의 모서리 반경 */
}

.main::-webkit-scrollbar-thumb:hover {
  background-color: #555; /* 스크롤 바 썸의 호버 상태 색상 */
}

.video-container-wrapper {
  min-width: 100%;

  width: 118.8rem;
}
.video:hover {
  transform: scale(1.05);
  cursor: pointer;
  opacity: 1;
}

.video-iframe {
  /* grid-row: 1 / span 5; */
  width: 100%;
  height: 100%;
  /* object-fit: cover; */
  border-radius: 10px;
  display: block;
  margin: auto;
}

.video {
  position: relative;
  width: 30rem;
  height: 22.5rem;
}

.video-title {
  position: absolute;
  bottom: 0;
  left: 0;
  width: 100%;
  padding: 0.3rem;
  font-size: x-large;
  font-weight: bold;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  background-color: rgba(78, 76, 76, 0.8);
  color: #fff;
  border-radius: 10px;
  font-size: 15px;
}

.divide-line {
  color: gray;
  border-style: solid;
  border-width: 1px;
  width: 112rem;
}
</style>
