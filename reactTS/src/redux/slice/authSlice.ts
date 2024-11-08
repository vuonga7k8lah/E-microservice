
import { createSlice, PayloadAction} from '@reduxjs/toolkit';
import {loginResponse,user} from '../../types/auth.type'

interface AuthState {
    accessToken: string | null;
    refreshToken: string | null;
    isAuthenticated: boolean;
    user: user | null;
}

const initialState: AuthState = {
    accessToken: null,
    refreshToken: null,
    isAuthenticated: false,
    user: null,
};
//Tạo hàm handleLogin với ReDux thunk
// export const handleLoginThunk = createAsyncThunk(
//     "authSlice/login",
//     async (values: {
//         username: string;
//         password: string;
//     }): Promise<ApiResponseType<UserLoginResponeType>> => {
//         try {
//             const data = await userLognApi(values);
//             return data;
//         } catch (error) {
//             console.log(error);
//             throw error;
//         }
//     }
// );

const authSlice = createSlice({
    name: 'auth',
    initialState,
    reducers: {
        loginSuccess(state, action: PayloadAction<loginResponse>) {
            state.accessToken = action.payload.accessToken;
            state.refreshToken = action.payload.refreshToken;
            state.isAuthenticated = true;
            state.user = action.payload.user;
        },
        logout(state) {
            state.accessToken = null;
            state.refreshToken = null;
            state.isAuthenticated = false;
            state.user = null;
        },
    },
});

export const { loginSuccess, logout } = authSlice.actions;

export default authSlice.reducer;
