import Vue from "vue";
import Vuex from "vuex";
import axios from "axios";
import router from "@/router";

Vue.use(Vuex);

const DEFAULT_URL = "http://localhost:9999";

// 에러처리용
function handleError(error, context) {
  console.log(error);

  // 잘못된 요청의 경우 status가 400
  if (error?.response?.status === 400) {
    alert("잘못된 접근입니다");
  }
  // 인증이 deny됐을 경우 status가 403
  else if (error?.response?.request?.status === 403) {
    if (context.state.JWT === null) {
      alert("로그인이 필요합니다");
    } else {
      alert("로그인이 만료되었습니다.");
      context.commit("LOGOUT");
    }
    // 메인으로 돌려보내기
    context.commit("SET_SELECTED_MENU", "메인");
  }
  if (router.currentRoute.path !== "/main") {
    router.push("/main");
  } else {
    location.reload();
  }
}

export default {
  state: {
    // 로그인 영역
    JWT: null,
    user: {},
    // 헤더 영역
    selectedMenu: "",
    // 보드등록 영역
    boardId: 0,
    // 보드리스트 영역
    boardList: [],
    maxPage: 0,
    currentPage: 1,
    searchFlag: false,
    // 보드디테일 영역
    board: {},
    reviewList: [],
    likeCnt: 0,
    checkLiked: false,
    // 회원가입 영역
    idChecked: 0,
    // 마이페이지 영역
    isUpdating: true,
    idols: [],
    fans: [],
    friends: [],
    searchFriends: [],
    // 메인페이지 영역
    remainKcal: 0,
  },

  getters: {},

  mutations: {
    LOGOUT(state) {
      state.JWT = null;
    },
    SET_SELECTED_MENU(state, menu) {
      state.selectedMenu = menu;
    },
    SET_BOARD_LIST(state, boardList) {
      state.boardList = boardList.boardlist;
      state.maxPage = boardList.maxPage;
    },
    SET_CURRENT_PAGE(state, page) {
      state.currentPage = page;
    },
    SET_SEARCH_FLAG(state, flag) {
      state.searchFlag = flag;
    },
    SET_BOARD(state, board) {
      state.board = board;
    },
    SET_REVIEW_LIST(state, reviewList) {
      state.reviewList = reviewList;
    },
    SET_LIKE_CNT(state, likeCnt) {
      state.likeCnt = likeCnt;
    },
    SET_ID_CHECK(state, param) {
      state.idChecked = param;
    },
    SET_JWT(state, JWT) {
      state.JWT = JWT;
    },
    SET_LOGIN_USER(state, user) {
      state.user = user;
    },
    SET_IS_UPDATING(state, update) {
      state.isUpdating = update;
    },
    SET_IDOLS(state, idols) {
      state.idols = idols;
    },
    SET_FANS(state, fans) {
      state.fans = fans;
    },
    SET_SEARCH_FRIENDS(state, searchFriends) {
      state.searchFriends = searchFriends;
    },
    SET_BOARD_ID(state, boardId) {
      state.boardId = boardId;
    },
    SET_REMAINKCAL(state, remainKcal) {
      state.user.remainKcal = remainKcal;
      state.remainKcal = remainKcal;
    },
    SET_CHECKLIKED(state, checkLiked) {
      state.checkLiked = checkLiked;
    },
  },

  actions: {
    // 로그아웃
    logout({ commit }) {
      if (confirm("오늘 운동은 모두 마치셨나요?")) {
        alert("내일 또 만나요!");
        commit("LOGOUT");
      }
    },

    // HeaderMenu 이동
    selectMenu({ commit }, menu) {
      commit("SET_SELECTED_MENU", menu);
    },

    // BoardList 가져오기
    getBoardList({ commit, state }, page) {
      // 페이지 없이 들어왔을 경우엔 1로 설정
      if (!page) page = 1;
      const URL = DEFAULT_URL + `/board/${page}`;

      axios({
        url: URL,
        method: "GET",
        headers: {
          Authorization: "Bearer " + state.JWT,
        },
      })
        .then(({ data }) => {
          commit("SET_SEARCH_FLAG", false);
          commit("SET_BOARD_LIST", data);
          commit("SET_CURRENT_PAGE", page);
        })
        .catch((err) => {
          handleError(err, this);
        });
    },

    // 게시글 검색
    searchBoard({ commit, state }, input) {
      const URL = DEFAULT_URL + `/board/search/${input.word}/${input.page}`;

      axios({
        url: URL,
        method: "GET",
        headers: {
          Authorization: "Bearer " + state.JWT,
        },
      })
        .then(({ data }) => {
          commit("SET_SEARCH_FLAG", true);
          commit("SET_BOARD_LIST", data);
          commit("SET_CURRENT_PAGE", input.page);
        })
        .catch((error) => {
          handleError(error, this);
        });
    },

    // 게시글 상세화면 가기 위해 가져오기
    getBoard({ commit, state }, boardId) {
      const URL = DEFAULT_URL + `/board/detail/${boardId}`;

      axios({
        url: URL,
        method: "GET",
        headers: {
          Authorization: "Bearer " + state.JWT,
        },
      })
        .then(({ data }) => {
          commit("SET_BOARD", data);
        })
        .catch((error) => {
          handleError(error, this);
        });
    },

    // 게시글의 리뷰 가져오기
    getReviews({ commit, state }, boardId) {
      const URL = DEFAULT_URL + `/review/${boardId}`;

      axios({
        url: URL,
        headers: {
          Authorization: "Bearer " + state.JWT,
        },
      })
        .then(({ data }) => {
          commit("SET_REVIEW_LIST", data);
        })
        .catch((error) => {
          handleError(error, this);
        });
    },

    // 보드 수정
    updateBoard({ commit, state }, board) {
      const URL = DEFAULT_URL + `/board`;

      const replaceStr = board.boardContent.replace(/(?:\r\n|\r|\n)/g, "<br>");
      board.boardContent = replaceStr;

      axios({
        url: URL,
        method: "PUT",
        data: board,
        headers: {
          Authorization: "Bearer " + state.JWT,
        },
      })
        .then(({ data }) => {
          console.log(data);
          commit("SET_BOARD", board);
        })
        .catch((error) => {
          handleError(error, this);
        });
    },

    // 좋아요 개수 가져오기
    getLikeCnt({ commit, state }, boardId) {
      const URL = DEFAULT_URL + `/likeBoard/${boardId}`;

      axios({
        url: URL,
        headers: {
          Authorization: "Bearer " + state.JWT,
        },
      })
        .then(({ data }) => {
          commit("SET_LIKE_CNT", data);
        })
        .catch((error) => {
          handleError(error, this);
        });
    },

    // 게시글 삭제
    deleteBoard({ commit, state }, boardId) {
      const URL = DEFAULT_URL + `/board/${boardId}`;

      axios({
        url: URL,
        method: "DELETE",
        headers: {
          Authorization: "Bearer " + state.JWT,
        },
      })
        .then(() => {
          commit("SET_BOARD", null);
        })
        .catch((error) => {
          handleError(error, this);
        });
    },

    // 리뷰 삭제
    deleteReview({ commit, state }, reviewId) {
      const URL = DEFAULT_URL + `/review/${reviewId}`;

      axios({
        url: URL,
        method: "DELETE",
        headers: {
          Authorization: "Bearer " + state.JWT,
        },
      })
        .then(() => {
          const updatedReviewList = state.reviewList.filter(
            (review) => review.reviewId !== reviewId
          );
          commit("SET_REVIEW_LIST", updatedReviewList);
        })
        .catch((error) => {
          handleError(error, this);
        });
    },

    // 아이디 중복 체크
    checkId({ commit }, userId) {
      const URL = DEFAULT_URL + `/user/join/check/${userId}`;

      axios({
        url: URL,
        method: "GET",
      })
        .then(({ data }) => {
          if (data) {
            commit("SET_ID_CHECK", 1);
          } else {
            commit("SET_ID_CHECK", 2);
          }
        })
        .catch((error) => {
          handleError(error, this);
        });
    },
    // 회원가입
    signUp({ commit }, user) {
      const URL = DEFAULT_URL + `/user/join`;

      return new Promise((resolve, reject) => {
        axios({
          url: URL,
          method: "POST",
          data: user,
        })
          .then(({ data }) => {
            console.log(data);
            commit;
            resolve();
          })
          .catch((error) => {
            handleError(error, this);
            reject(error);
          });
      });
    },
    // 회원정보 수정
    updateUser({ commit, state }, user) {
      const URL = DEFAULT_URL + `/user`;

      return new Promise((resolve, reject) => {
        axios({
          url: URL,
          method: "PUT",
          data: user,
          headers: {
            Authorization: "Bearer " + state.JWT,
          },
        })
          .then(({ data }) => {
            console.log(data);
            commit;
            resolve();
          })
          .catch((error) => {
            handleError(error, this);
            reject(error);
          });
      });
    },
    // 사진 등록
    uploadUserImg({ commit }, data) {
      const URL = DEFAULT_URL + `/user/img/${data.get("userId")}`;

      const formData = new FormData();
      formData.append("imgFile", data.get("imgFile"));

      return new Promise((resolve, reject) => {
        axios
          .post(URL, formData, {
            headers: {
              "Content-Type": "multipart/form-data",
            },
          })
          .then(({ data }) => {
            console.log(data);
            commit;
            resolve(); // 사진 등록 완료 후 Promise resolve
          })
          .catch((error) => {
            handleError(error, this);
            reject(error); // 에러가 발생한 경우 Promise reject
          });
      });
    },
    // 로그인
    loginUser({ commit }, user) {
      const URL = DEFAULT_URL + "/user/login";

      return new Promise((resolve, reject) => {
        axios({
          url: URL,
          method: "POST",
          data: user,
        })
          .then(({ data, headers }) => {
            commit("SET_JWT", headers.authorization);
            commit("SET_LOGIN_USER", data);
            resolve();
          })
          .then(() => {
            router.push("/main");
          })
          .catch((error) => {
            handleError(error, this);
            reject(error);
          });
      });
    },
    // 나를 팔로잉한 사람 데려오기
    getFans({ commit, state }, userId) {
      const URL = DEFAULT_URL + `/follow/followed/${userId}`;

      axios({
        url: URL,
        method: "GET",
        headers: {
          Authorization: "Bearer " + state.JWT,
        },
      })
        .then(({ data }) => {
          commit("SET_FANS", data);
        })
        .catch((error) => {
          handleError(error, this);
        });
    },

    // 내가 팔로잉한 사람 데려오기
    getIdols({ commit, state }, userId) {
      const URL = DEFAULT_URL + `/follow/${userId}`;

      axios({
        url: URL,
        method: "GET",
        headers: {
          Authorization: "Bearer " + state.JWT,
        },
      })
        .then(({ data }) => {
          commit("SET_IDOLS", data);
        })
        .catch((error) => {
          handleError(error, this);
        });
    },

    // 이름으로 회원 찾기
    searchFriends({ commit, state }, params) {
      const URL = DEFAULT_URL + `/follow/info`;

      axios({
        url: URL,
        method: "GET",
        params: {
          userName: params.searchWord,
          userId: params.userId,
        },
        headers: {
          Authorization: "Bearer " + state.JWT,
        },
      })
        .then(({ data }) => {
          commit("SET_SEARCH_FRIENDS", data);
        })
        .catch((error) => {
          handleError(error, this);
        });

      commit;
    },

    // 팔로우하기
    followUser({ commit, state }, params) {
      const URL = DEFAULT_URL + "/follow";

      axios({
        url: URL,
        method: "POST",
        params: {
          userId: params.userId,
          friendId: params.friendId,
        },
        headers: {
          Authorization: "Bearer " + state.JWT,
        },
      })
        .then(() => {
          let friendList = state.searchFriends;
          for (let i = 0; i < friendList.length; i++) {
            if (friendList[i].userId === params.friendId) {
              friendList.splice(i, 1);
              break;
            }
          }
          commit("SET_SEARCH_FRIENDS", friendList);
        })
        .catch((error) => {
          handleError(error, this);
        });
    },

    // 팔로우 취소
    unfollowUser({ commit, state }, params) {
      const URL = DEFAULT_URL + "/follow";

      axios({
        url: URL,
        method: "DELETE",
        params: {
          userId: params.userId,
          friendId: params.friendId,
        },
        headers: {
          Authorization: "Bearer " + state.JWT,
        },
      })
        .then(() => {
          let idolList = state.idols;
          for (let i = 0; i < idolList.length; i++) {
            if (idolList[i].userId === params.friendId) {
              idolList.splice(i, 1);
              break;
            }
          }
          commit("SET_IDOLS", idolList);
        })
        .catch((error) => {
          handleError(error, this);
        });
    },
    // 유저 정보 가져오기
    getUserData({ commit, state }, userId) {
      const URL = DEFAULT_URL + `/user/UnSensitiveData/${userId}`;

      axios({
        url: URL,
        method: "GET",
        headers: {
          Authorization: "Bearer " + state.JWT,
        },
      })
        .then(({ data }) => {
          commit("SET_LOGIN_USER", data);
        })
        .catch((error) => {
          handleError(error, this);
        });
    },
    // 회원탈퇴
    removeUser({ commit, state }, params) {
      const URL = DEFAULT_URL + "/user";

      axios({
        url: URL,
        method: "DELETE",
        data: params,
        headers: {
          Authorization: "Bearer " + state.JWT,
        },
      })
        .then(() => {
          commit("LOGOUT");
        })
        .catch((error) => {
          handleError(error, this);
        });
    },

    // 게시글 등록
    registBoard({ commit, state }, board) {
      const URL = DEFAULT_URL + "/board";

      return new Promise((resolve, reject) => {
        axios({
          url: URL,
          method: "POST",
          data: board,
          headers: {
            Authorization: "Bearer " + state.JWT,
          },
        })
          .then(({ data }) => {
            commit("SET_BOARD_ID", data);
            resolve();
          })
          .catch((error) => {
            handleError(error, this);
            reject(error);
          });
      });
    },
    // 사진 등록
    uploadBoardImg({ commit, state }, data) {
      const URL = DEFAULT_URL + `/board/img/${data.get("boardId")}`;

      const formData = new FormData();
      formData.append("imgFile", data.get("imgFile"));

      return new Promise((resolve, reject) => {
        axios
          .post(URL, formData, {
            headers: {
              "Content-Type": "multipart/form-data",
              Authorization: "Bearer " + state.JWT,
            },
          })
          .then(({ data }) => {
            console.log(data);
            commit;
            resolve(); // 사진 등록 완료 후 Promise resolve
          })
          .catch((error) => {
            handleError(error, this);
            reject(error); // 에러가 발생한 경우 Promise reject
          });
      });
    },
    // 리뷰 등록
    registReview({ commit, state }, params) {
      const URL = DEFAULT_URL + `/review`;

      axios({
        url: URL,
        method: "POST",
        data: params,
        headers: {
          Authorization: "Bearer " + state.JWT,
        },
      })
        .then(() => {
          commit;
        })
        .catch((error) => {
          handleError(error, this);
        });
    },
    // RemainKcal 가져오기
    getRemainKcal({ commit, state }, userId) {
      const URL = DEFAULT_URL + `/mealPlan/remain/${userId}`;

      axios({
        url: URL,
        method: "GET",
        headers: {
          Authorization: "Bearer " + state.JWT,
        },
      })
        .then(({ data }) => {
          commit("SET_REMAINKCAL", data);
        })
        .catch((error) => {
          handleError(error, this);
        });
    },
    // 좋아요 누르기
    likeBoard({ commit, state }, params) {
      const URL = DEFAULT_URL + `/likeBoard`;

      axios({
        url: URL,
        method: "POST",
        params: params,
        headers: {
          Authorization: "Bearer " + state.JWT,
        },
      })
        .then(() => {
          commit("SET_CHECKLIKED", true);
        })
        .catch((error) => {
          handleError(error, this);
        });
    },

    // 좋아요 취소
    dislikeBoard({ commit, state }, params) {
      const URL = DEFAULT_URL + `/likeBoard`;

      axios({
        url: URL,
        method: "DELETE",
        params: params,
        headers: {
          Authorization: "Bearer " + state.JWT,
        },
      })
        .then(() => {
          commit("SET_CHECKLIKED", false);
        })
        .catch((error) => {
          handleError(error, this);
        });
    },

    // 좋아요 체크
    checkLiked({ commit, state }, params) {
      const URL = DEFAULT_URL + `/likeBoard/check`;

      axios({
        url: URL,
        method: "GET",
        params: params,
        headers: {
          Authorization: "Bearer " + state.JWT,
        },
      })
        .then(({ data }) => {
          commit("SET_CHECKLIKED", data);
        })
        .catch((error) => {
          handleError(error, this);
        });
    },
  },
  modules: {},
};
