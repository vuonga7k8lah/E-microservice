import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import "./index.css";
import {
    LoginPage,
    UserPage,
    CategoryPage,
    ProductPage,
} from "./pages/index.tsx";
import Home from "./pages/shop/HomePage";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import { ToastProvider } from "./contexts/ToastContext";
import { ToastContainer } from "react-toastify";
import Layout from "./components/Layout/Layout";
import Shop from "./components/Layout/Shop.tsx";
import { Provider } from "react-redux";
import { PersistGate } from "redux-persist/integration/react";
import { store, persistor } from "./redux/store";
import PrivateRoute from "./components/PrivateRoute";

const router = createBrowserRouter([
    {
        path: "/admin",
        element: <Layout />,
        children: [
            {
                path: "users",
                element: (
                    <PrivateRoute>
                        <UserPage />
                    </PrivateRoute>
                ),
            },
            {
                path: "categories",
                element: (
                    <PrivateRoute>
                        <CategoryPage />
                    </PrivateRoute>
                ),
            },
            {
                path: "products",
                element: (
                    <PrivateRoute>
                        <ProductPage />
                    </PrivateRoute>
                ),
            },
        ],
    },
    {
        path: "/login",
        element: <LoginPage />,
    },
    {
        path: "/",
        element: <Shop />,
        children: [
            {
                path: "/",
                element: <Home />,
            },
        ],
    },
]);

createRoot(document.getElementById("root")!).render(
    <StrictMode>
        <Provider store={store}>
            <PersistGate loading={null} persistor={persistor}>
                <ToastProvider>
                    <RouterProvider router={router} />
                    <ToastContainer />
                </ToastProvider>
            </PersistGate>
        </Provider>
    </StrictMode>
);
