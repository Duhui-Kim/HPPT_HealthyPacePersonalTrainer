import { RouterView } from 'vue-router';

<template>
  <main>
    <article class="detail">
      <!-- 게시글 영역-->
      <div class="detail-container">
        <div class="title-wrapper">
          <input
            type="text"
            id="title"
            class="regist-title"
            v-model="boardTitle"
            ref="boardTitle"
            placeholder="제목"
          />
        </div>
        <div class="writer-wrapper">
          <div class="writer">작성자 : {{ user.userName }}</div>
        </div>
        <div class="content-wrapper">
          <textarea
            id="content"
            class="regist-content"
            v-model="boardContent"
            ref="boardContent"
            placeholder="내용"
          ></textarea>
        </div>

        <div class="regist-file-wrapper">
          <label for="regist-file">이미지 업로드</label>
          <input
            class="regist-file"
            id="regist-file"
            type="file"
            accept="image/*"
            @change="uploadFile"
          />
        </div>

        <!-- 게시글 조작 버튼 -->
        <div class="detail-container-board-review-buttons">
          <button
            class="registBtn"
            style="font-weight: bold"
            @click.prevent="registBoard"
          >
            등록
          </button>
          <button for="link" class="cancelBtn">
            <router-link
              id="link"
              to="/board"
              style="font-weight: bold; color: red"
              >취소</router-link
            >
          </button>
        </div>
      </div>
    </article>
  </main>
</template>

<script>
export default {
  name: "BoardRegist",

  data() {
    return {
      boardTitle: "",
      boardContent: "",
      imgFile: null,
    };
  },
  computed: {
    user() {
      return this.$store.state.hui.user;
    },
  },

  methods: {
    uploadFile(event) {
      const file = event.target.files[0];

      this.imgFile = file;
    },
    registBoard() {
      if (this.boardTitle.trim().length === 0) {
        alert("제목을 입력해주세요");
        this.$refs.boardTitle.focus();
      } else if (this.boardContent.trim().length === 0) {
        alert("내용을 입력해주세요");
        this.$refs.boardContent.focus();
      } else {
        if (!confirm("게시글을 등록하시겠습니까?")) return;

        const replaceStr = this.boardContent.replace(/(?:\r\n|\r|\n)/g, "<br>");
        let boardContent = replaceStr;

        let board = {
          userId: this.user.userId,
          boardTitle: this.boardTitle,
          boardContent: boardContent,
          boardWriter: this.user.userName,
        };

        this.$store
          .dispatch("registBoard", board)
          .then(() => {
            if (this.imgFile) {
              setTimeout(() => {
                const formData = new FormData();
                formData.append("imgFile", this.imgFile);
                formData.append("boardId", this.$store.state.hui.boardId);
                this.$store.dispatch("uploadBoardImg", formData);
              }, 300);
            }
          })
          .then(() => {
            setTimeout(() => {
              this.$router.push(
                `/board/detail/${this.$store.state.hui.boardId}`
              );
            }, 500);
          });
      }
    },
  },
};
</script>

<style scoped>
.detail-container-board-review-buttons {
  grid-row: 13 / span 1;
  padding: 0;
  display: grid;
  grid-template-columns: repeat(8, 3rem);
  justify-content: end;
  gap: 1.5rem;
  margin-right: 3.5rem;
}

.cancelBtn {
  grid-column: 8 / span 1;
  font-size: 1rem;
  border: 0px;
  width: 4rem;
  height: 2rem;
  color: #ff7777;
  border: 2px solid #f0f0f0;
  border-radius: 5px;
  transition: 0.4s ease;
  margin-bottom: 1rem;
  margin-right: 10rem;
}
.registBtn {
  grid-column: 7 / span 1;
  font-size: 1rem;
  border: 2px solid #f0f0f0;
  width: 4rem;
  height: 2rem;
  color: gray;
  border-radius: 5px;
  transition: 0.4s ease;
  margin-bottom: 1rem;
  margin-right: 10rem;
}
.cancelBtn:hover {
  cursor: pointer;
  border: 2px solid #ff7777;
  background-color: #ff7777;
  color: black;
  box-shadow: 2px 1px 4px rgba(134, 134, 134, 1);
}
.registBtn:hover {
  cursor: pointer;
  border: 2px solid #cde990;
  background-color: #cde990;
  color: #ffffff;
  box-shadow: 2px 1px 4px rgba(134, 134, 134, 1);
}
.detail {
  display: flex;
  justify-content: center;
  margin-bottom: 6rem;
}

.detail-container {
  display: grid;
  grid-template-rows: repeat(6, 2rem);
  gap: 0.5rem;
  border: 1px solid #f2f2f2;
  border-radius: 30px;
  width: 40rem;
  height: 30rem;
  overflow: hidden;
  background-color: #f2f2f2;
  display: grid;
  grid-template-rows: repeat(7, 2rem);
  align-items: center;
  box-shadow: 1px 1px 7px rgb(165, 164, 164);
}

.title-wrapper {
  margin-top: 5rem;
  border: white;
  width: 100%;
  display: flex;
  justify-content: center;
}

.title-wrapper input {
  border: 1px white;
  border-radius: 9px;
  width: 90%;
  height: 1.7rem;
  padding: 0.5rem;
  font-size: 1rem;
  box-shadow: 1px 1px 7px rgb(165, 164, 164);
}

.writer-wrapper {
  margin-top: 5.5rem;
  margin-right: 2rem;

  display: flex;
  justify-content: right;
  flex: 1;
}

.writer-wrapper {
  font-weight: bold;
  color: gray;
}

.content-wrapper {
  margin-top: 24rem;

  display: flex;
  justify-content: center;
}

.content-wrapper textarea {
  border: 1px white;
  border-radius: 10px;

  border: 1px white;
  border-radius: 5px;
  width: 90%;
  height: 14rem;
  padding: 0.5rem;
  font-size: 1rem;
  box-shadow: 1px 1px 7px rgb(165, 164, 164);
}

.regist-file-wrapper {
  margin-top: 1rem;
  display: flex;
  justify-content: right;
  margin-right: 2rem;
  height: 2rem;
}
.regist-file-wrapper label {
  display: flex;
  align-items: center;
  padding: 0.5rem;
  color: #ffffff;
  font-weight: bold;

  background-color: #aacb73;
  cursor: pointer;

  border-radius: 0.4em;
  box-shadow: 2px 1px 4px rgba(134, 134, 134, 1);
}

.regist-file-wrapper label:hover {
  background-color: #7bb126;
}

.regist-file-wrapper input[type="file"] {
  position: absolute;
  width: 2rem;
  height: 2rem;
  visibility: hidden;
}
</style>
