import { configureStore } from "@reduxjs/toolkit";
import notificationReducer from "../features/Notifcations/NotificationSlice";
import authReducer from "../features/Auth/AuthSlice";
import queryParamsReducer from "../features/QueryParams/QueryParamsSlice";
import authTokenReducer from "../features/AuthToken/AuthTokenSlice";

export const store = configureStore({
  reducer: {
    notificaton: notificationReducer,
    auth: authReducer,
    queryParams: queryParamsReducer,
    authToken: authTokenReducer,
  },
});

// Infer the `RootState` and `AppDispatch` types from the store itself
export type RootState = ReturnType<typeof store.getState>;
// Inferred type: {posts: PostsState, comments: CommentsState, users: UsersState}
export type AppDispatch = typeof store.dispatch;
