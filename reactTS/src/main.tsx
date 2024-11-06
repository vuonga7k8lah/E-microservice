import {StrictMode} from "react";
import {createRoot} from "react-dom/client";
import "./index.css";
import {LoginPage, UserPage,CategoryPage} from "./pages/index.tsx";
import {createBrowserRouter, RouterProvider} from "react-router-dom";
import {ToastProvider} from "./contexts/ToastContext";
import {ToastContainer} from "react-toastify";
import Layout from "./components/Layout/Layout";
import {Provider} from "react-redux";
import {store} from "./redux/store";


const router = createBrowserRouter([
    {
        path: "/",
        element: (
                <Layout/>
        ),
        children: [
            {
                path: "/users",
                element: <UserPage/>,
            },
            {
                path: "/categories",
                element: <CategoryPage/>,
            }
        ]
    },
    {
        path: "/login",
        element: <LoginPage/>,
    },

]);
createRoot(document.getElementById("root")!).render(
    <StrictMode>
        <Provider store={store}>
            <ToastProvider>
                <RouterProvider router={router}/>
                <ToastContainer/>
            </ToastProvider>
        </Provider>
    </StrictMode>
);
