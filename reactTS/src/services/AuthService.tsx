import { ToastOptions } from "react-toastify";

import { loginSuccess } from "../redux/slice/authSlice";
import { loginResponse } from "../types/auth.type";

import axios, { AxiosInstance } from "axios";

type LoginPayload = {
    username: string;
    password: string;
};

const login = async (
    payload: LoginPayload,
    notify: (message: string, options?: ToastOptions) => void,
    dispatch: any
) => {
    try {
        const axiosInstance: AxiosInstance = axios.create({
            baseURL: import.meta.env.VITE_API_URL || "http://localhost:8083/",
            timeout: 10000, //
            headers: {
                "Content-Type": "application/json",
                "Access-Control-Allow-Origin": "*",
            },
        });
        const response = await axiosInstance.post<LoginPayload[]>(
            "/api/v1/customers/auth",
            payload
        );
        if (response.status === 200) {
            console.log(response.data);

            const data: loginResponse = {
                // @ts-ignore
                accessToken: response.data.info.access_token,
                // @ts-ignore
                refreshToken: response.data.info.refresh_token,
                isAuthenticated: true,
            };

            // @ts-ignore
            notify(response.data.message || "Login successful!", {
                type: "success",
            });
            dispatch(loginSuccess(data));
            return true;
        }
    } catch (error) {
        if (axios.isAxiosError(error)) {
            if (error.response && error.response.data.message) {
                notify(error.response.data.message, { type: "error" });
            } else {
                notify("An unexpected error occurred. Please try again.", {
                    type: "error",
                });
            }
        }
        return false;
    }
};

export { login };
