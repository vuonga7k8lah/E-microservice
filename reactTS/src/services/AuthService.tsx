import axiosInstance from "../configs/axios";
import axios from "axios";
import {ToastOptions} from "react-toastify";

import {loginSuccess} from '../redux/slice/authSlice'
import {loginResponse} from '../types/auth.type'


type LoginPayload = {
    email: string;
    password: string;
};


const login = async (payload: LoginPayload, notify: (message: string, options?: ToastOptions) => void, dispatch: any) => {
    try {
        const response = await axiosInstance.post<LoginPayload[]>("/login", payload);
        if (response.status === 200) {
            const data:loginResponse = {
                // @ts-ignore
                accessToken:response.data.data.accessToken,
                // @ts-ignore
                refreshToken:response.data.data.refreshToken,
                user:{
                    // @ts-ignore
                    id:response.data.data.user.id,
                    // @ts-ignore
                    name:response.data.data.user.name,
                },
                isAuthenticated:true
            }

            // @ts-ignore
            notify(response.data.message || 'Login successful!', { type: 'success' });
            dispatch(loginSuccess(data))
            return true;
        }
    } catch (error) {
        if (axios.isAxiosError(error)) {
            if (error.response && error.response.data.message) {
                notify(error.response.data.message, { type: 'error' });
            } else {
                notify('An unexpected error occurred. Please try again.', { type: 'error' });
            }
        }
        return false;
    }
};

export { login };
