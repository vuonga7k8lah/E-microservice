import React from "react";
import {Outlet} from "react-router-dom";

const layout: React.FC = () => {
    return (
        <>
            <h2>day la layout tong</h2>
            <Outlet/>
        </>
    );
}
export default layout;
