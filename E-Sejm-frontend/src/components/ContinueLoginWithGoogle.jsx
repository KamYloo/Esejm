import { useEffect } from "react";
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";

import {loginWithGoogleAction} from "../redux/AuthService/Action.js";
import toast from "react-hot-toast";

const ContinueLoginWithGoogle = () => {
    const dispatch = useDispatch();
    const navigate = useNavigate();

    useEffect(() => {
        const shouldLogin = localStorage.getItem("loginWithGoogle");

        if (shouldLogin) {
            localStorage.removeItem("loginWithGoogle");

            dispatch(loginWithGoogleAction())
                .then(() => {
                    navigate("/stronaGlowna");
                    toast.success("You have logged in successfully.");
                })
                .catch(() => {
                    toast.error("Failed to login. Please try again.");
                });
        }
    }, [dispatch, navigate]);

    return null;
};

export default ContinueLoginWithGoogle;
