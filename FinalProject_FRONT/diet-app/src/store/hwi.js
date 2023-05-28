import Vue from "vue";
import Vuex from "vuex";
import router from "@/router";
import axios from "axios";

Vue.use(Vuex);

const DEFAULT_URL = "http://localhost:9999";

// 에러처리용
function handleError(error, context, rootState) {
  // 잘못된 요청의 경우 status가 400
  if (error?.response?.status === 400) {
    alert("잘못된 접근입니다");
  }
  // 인증이 deny됐을 경우 status가 403
  else if (error?.response?.request?.status === 403) {
    if (rootState.hui.JWT === null) {
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
    //운동영상 페이지
    videos: [],
    video: {},
    //식단 페이지
    meal: {},
    //식단 페이지_식사 가져오기
    total: [],
    morning: [],
    lunch: [],
    dinner: [],

    // 로그인 후 메인
    BaseKcal: 0,
    TotalKcal: 0,

    Kcal: [0],
    Carbo: [0],
    Protein: [0],
    Fat: [0],

    //식단 페이지_ 식사 등록_ 검색한 메뉴 가져오기
    searchedMenus: [],
    mealId: 0,
  },
  getters: {},
  mutations: {
    SET_VIDEOS(state, videos) {
      state.videos = videos;
    },
    SET_VIDEO(state, video) {
      state.video = video;
    },
    //식단 페이지_식사 가져오기
    SET_TOTAL(state, total) {
      state.total = total;
    },
    SET_MORNING(state, morning) {
      state.morning = morning;
    },
    SET_LUNCH(state, lunch) {
      state.lunch = lunch;
    },
    SET_DINNER(state, dinner) {
      state.dinner = dinner;
    },
    // 영양정보 추가
    SET_KCAL(state, Kcal) {
      state.Kcal = [Kcal];
    },
    SET_CARBO(state, Carbo) {
      state.Carbo = [Carbo];
    },
    SET_PROTEIN(state, Protein) {
      state.Protein = [Protein];
    },
    SET_FAT(state, Fat) {
      state.Fat = [Fat];
    },
    SET_BASEKCAL(state, BaseKcal) {
      state.BaseKcal = BaseKcal;
    },
    SET_TOTALKCAL(state, TotalKcal) {
      state.TotalKcal = TotalKcal;
    },
    SET_MEALID(state, mealId) {
      state.mealId = mealId;
    },

    //식단 페이지_식사 삭제
    DELETE_MEAL(state, { mealId, num }) {
      if (num === 1) {
        state.morning = state.morning.filter((food) => food.mealId !== mealId);
      }
      if (num === 2) {
        state.lunch = state.lunch.filter((food) => food.mealId !== mealId);
      }
      if (num === 3) {
        state.dinner = state.dinner.filter((food) => food.mealId != mealId);
      }
    },

    //식단 페이지_ 식사 등록_ 검색한 메뉴 가져오기
    SET_SEARCHEDMENUS(state, searchedMenus) {
      state.searchedMenus = searchedMenus;
    },
  },
  actions: {
    //운동영상 페이지에 로드할 영상들 가져오기
    searchVideos({ commit, rootState }, query) {
      const URL = DEFAULT_URL + "/youtube";
      axios({
        url: URL,
        method: "GET",
        params: { query: query },
      })
        .then(({ data }) => {
          commit("SET_VIDEOS", data);
        })
        .catch((error) => {
          handleError(error, this, rootState);
        });
    },

    //운동영상 페이지에서 영상 하나를 클릭했을 때 모달에 띄울 영상 가져오기
    getVideo({ commit, state }, videoId) {
      const videos = state.videos;

      for (let i = 0; i < videos.length; i++) {
        if (videos[i].videoId === videoId) {
          commit("SET_VIDEO", videos[i]);
          break;
        }
      }
    },

    //메인 화면에 랜덤으로 출력할 운동 영상 가져오기
    searchVideosForMain({ commit, rootState }, param) {
      const URL = DEFAULT_URL + "/youtube";
      axios({
        url: URL,
        method: "GET",
        params: { query: param.query, maxResults: param.maxResults },
      })
        .then(({ data }) => {
          commit("SET_VIDEOS", data);
        })
        .catch((error) => {
          handleError(error, this, rootState);
        });
    },

    //식사 등록
    registMeal({ commit, rootState }, meal) {
      const URL = DEFAULT_URL + "/mealPlan";

      return new Promise((resolve, reject) => {
        axios({
          url: URL,
          method: "POST",
          params: meal,
          headers: {
            Authorization: "Bearer " + rootState.hui.JWT,
          },
        })
          .then(({ data }) => {
            commit("SET_MEALID", data.mealId);
            resolve();
          })
          .catch((error) => {
            handleError(error, this, rootState);
            reject(error);
          });
      });
    },
    uploadMealImg({ commit, rootState }, data) {
      const URL = DEFAULT_URL + `/mealPlan/img/${data.get("mealId")}`;

      const formData = new FormData();
      formData.append("imgFile", data.get("imgFile"));

      return new Promise((resolve, reject) => {
        axios
          .post(URL, formData, {
            headers: {
              "Content-Type": "multipart/form-data",
              Authorization: "Bearer " + rootState.hui.JWT,
            },
          })
          .then(({ data }) => {
            console.log(data);
            commit;
            resolve(); // 사진 등록 완료 후 Promise resolve
          })
          .catch((error) => {
            handleError(error, this, rootState);
            reject(error); // 에러가 발생한 경우 Promise reject
          });
      });
    },
    //식사 가져오기
    getUserMeal({ commit, rootState }, param) {
      const URL = DEFAULT_URL + `/mealPlan/${param.userId}/${param.date}`;
      axios({
        url: URL,
        method: "GET",
        headers: {
          Authorization: "Bearer " + rootState.hui.JWT,
        },
      })
        .then(({ data }) => {
          commit("SET_TOTAL", data.total);
          commit("SET_MORNING", data.morning);
          commit("SET_LUNCH", data.lunch);
          commit("SET_DINNER", data.dinner);
        })
        .catch((error) => {
          handleError(error, this, rootState);
        });
    },

    //식사 삭제
    deleteMeal({ commit, rootState }, param) {
      const URL = DEFAULT_URL + `/mealPlan/${param.mealId}`;
      axios({
        url: URL,
        method: "DELETE",
        headers: {
          Authorization: "Bearer " + rootState.hui.JWT,
        },
      })
        .then(() => {
          commit("DELETE_MEAL", param);
        })
        .catch((error) => {
          handleError(error, this, rootState);
        });
    },
    //메뉴 검색
    searchMenu({ commit, rootState }, menuName) {
      const URL = DEFAULT_URL + `/mealPlan/search/${menuName}`;
      axios({
        url: URL,
        method: "GET",
        headers: {
          Authorization: "Bearer " + rootState.hui.JWT,
        },
      })
        .then(({ data }) => {
          commit("SET_SEARCHEDMENUS", data);
        })
        .catch((error) => {
          handleError(error, this, rootState);
        });
    },
    // 영양소 데이터 가공
    updateNutri({ commit, state, rootState }) {
      let Kcal = 0;
      let Carbo = 0;
      let Protein = 0;
      let Fat = 0;
      let base = 0;
      let list = [];
      list = state.total;

      for (let i = 0; i < list.length; i++) {
        Kcal += list[i].menuKcal;
        Carbo += list[i].carbo;
        Protein += list[i].protein;
        Fat += list[i].fat;
      }
      commit("SET_TOTALKCAL", Kcal);

      const userSex = rootState.hui.user.userSex;
      if (userSex === 1) {
        base = 73 * 0.9 * 32.5 - 500;
      } else {
        base = 60 * 0.9 * 32.5 - 500;
      }

      let baseCarbo = base / 10;
      let baseProtein = base / 10;
      let baseFat = (base * 2) / 90;

      Kcal = (Kcal / base) * 100;
      Carbo = (Carbo / baseCarbo) * 100;
      Protein = (Protein / baseProtein) * 100;
      Fat = (Fat / baseFat) * 100;

      commit("SET_KCAL", parseFloat(Kcal.toFixed(2)));
      commit("SET_CARBO", parseFloat(Carbo.toFixed(2)));
      commit("SET_PROTEIN", parseFloat(Protein.toFixed(2)));
      commit("SET_FAT", parseFloat(Fat.toFixed(2)));
      commit("SET_BASEKCAL", parseFloat(base.toFixed(2)));
    },
  },
  modules: {},
};
