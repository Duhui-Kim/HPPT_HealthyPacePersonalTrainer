import Vue from "vue";
import VueRouter from "vue-router";
import WelcomeView from "../views/Welcome.vue";
import MainPageView from "../views/MainPage.vue";
import MealPlanPageView from "../views/MealPlanPage.vue";
import VideoPageView from "../views/VideoPage.vue";
import BoardPageView from "../views/BoardPage.vue";
import MyPageView from "../views/MyPage.vue";
import LoginPageView from "../views/LoginPage.vue";
import SignUpPageView from "../views/SignUpPage.vue";

// BoardPage의 하위 컴포넌트
import BoardList from "../components/Board/BoardList.vue";
import BoardDetail from "../components/Board/BoardDetail.vue";
import BoardRegist from "../components/Board/BoardRegist.vue";

// BoardDetail의 하위 컴포넌트
import BoardMain from "../components/Board/include/BoardMain.vue";
import BoardUpdate from "../components/Board/include/BoardUpdate.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Welcome",
    component: WelcomeView,
  },
  {
    path: "/main",
    name: "MainPage",
    component: MainPageView,
  },
  {
    path: "/meal",
    name: "MealPlanPage",
    component: MealPlanPageView,
  },
  {
    path: "/video",
    name: "VideoPage",
    component: VideoPageView,
  },
  {
    path: "/board",
    name: "BoardPage",
    component: BoardPageView,
    children: [
      {
        path: "",
        name: "BoardList",
        component: BoardList,
      },
      {
        path: "detail/:boardId",
        name: "BoardDetail",
        component: BoardDetail,
        children: [
          {
            path: "/board/detail/:boardId",
            name: "BoardMain",
            component: BoardMain,
          },
          {
            path: "/board/detail/:boardId",
            name: "BoardUpdate",
            component: BoardUpdate,
          },
        ],
      },
      {
        path: "regist",
        name: "BoardRegist",
        component: BoardRegist,
      },
    ],
  },
  {
    path: "/mypage",
    name: "MyPage",
    component: MyPageView,
  },
  {
    path: "/login",
    name: "LoginPage",
    component: LoginPageView,
  },
  {
    path: "/signup",
    name: "SignUpPage",
    component: SignUpPageView,
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
