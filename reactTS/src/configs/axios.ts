import axios, { AxiosInstance } from "axios";

// Tạo instance của axios với cấu hình mặc định
const axiosInstance: AxiosInstance = axios.create({
    baseURL: "https://vtheme.laravel/api/v1/",
    timeout: 10000, //
    headers: {
        "Content-Type": "application/json",
    },
});

axiosInstance.interceptors.request.use(
    (config) => {
        // Ví dụ: Thêm token vào header nếu cần thiết
        const token = localStorage.getItem("token");
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

// Thêm interceptor cho response
axiosInstance.interceptors.response.use(
    (response) => {
        return response;
    },
    (error) => {
        if (error.response && error.response.status === 401) {

            console.log("Unauthorized, logging out...");
        }
        return Promise.reject(error);
    }
);

export default axiosInstance;
