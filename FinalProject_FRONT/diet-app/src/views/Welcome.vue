<template>
  <div class="home">
    <body>
      <div class="container" ref="container" :class="{ hidden: isHidden }">
        <img class="container-img" src="../assets/Welcome.png" />
        <a class="container-a" @click.prevent="start">시작하기</a>
      </div>
    </body>
  </div>
</template>

<script>
export default {
  name: "WelcomeView",
  data() {
    return {
      isHidden: false,
    };
  },
  methods: {
    start() {
      this.isHidden = true;
      setTimeout(() => {
        this.$router.push("/main");
      }, 500);
    },
  },
  components: {},
};
</script>

<style scoped>
* {
  background-color: #ffffe8;
}

a {
  text-decoration: none;
  background-color: #ecece0;
  padding: 0.5rem 1.5rem 0.5rem 1.5rem;
  border: 2px solid #ecece0;
  color: #000000;
  border-radius: 20px;
}

.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh;
  background-color: #ffffe8;
  opacity: 1;
  transition: opacity 0.5s ease;
}

.container-img {
  width: 40rem;
}

.container-a {
  margin-top: 3rem;
  font-size: 2rem;
  opacity: 1;
  transition: 0.5s ease;
}

.container-a:hover {
  cursor: pointer;
  border: 2px solid #ffd4d4;
  background-color: #ffd4d4;
  color: #ffffff;
}

.hidden {
  opacity: 0;
}
</style>
