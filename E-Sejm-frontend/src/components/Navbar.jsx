import React, { useEffect, useState } from "react";
import { Link, useLocation, useNavigate } from "react-router-dom";
import logo from "../assets/logoEsejm.png";
import { AiOutlineMenuFold } from "react-icons/ai";
import { MdCancel } from "react-icons/md";
import { FaUserCircle } from "react-icons/fa";
import { BiDotsVerticalRounded } from "react-icons/bi";
import { useDispatch, useSelector } from "react-redux";
import { logoutAction } from "../redux/AuthService/Action.js";
import toast from "react-hot-toast";

function Navbar() {
    const [isPanelVisible, setPanelVisible] = useState(false);
    const [isMenuOpen, setMenuOpen] = useState(false);
    const location = useLocation();
    const navigate = useNavigate();
    const dispatch = useDispatch();
    const [activeTab, setActiveTab] = useState("stronaGlowna");
    const {auth} = useSelector(store => store);
    const userData = (() => { try { return JSON.parse(localStorage.getItem("user")) || null; } catch { return null; } })();

    const togglePanel = () => {
        setPanelVisible(!isPanelVisible);
    };

    const toggleMenu = () => {
        setMenuOpen(!isMenuOpen);
    };

    const handleLogout = () => {
        dispatch(logoutAction())
            .then(() => {
                navigate("/logowanie");
                toast.success(auth.logout || 'Logged out successfully');
            })
            .catch(() => {
                toast.error("Failed to logout. Please try again.");
            })
    };

    useEffect(() => {
        const currentPath = location.pathname.replace("/", "") || "stronaGlowna";
        setActiveTab(currentPath);
    }, [location]);

    return (
        <header className="bg-[#061D31] h-24 flex items-center px-8 relative shadow-md shadow-[#00000088]">
            <img className="h-16 object-cover" src={logo} alt="Logo"/>

            <div className="flex lg:hidden z-[2] ml-auto">
                <button onClick={togglePanel} className="text-white text-3xl">
                    {isPanelVisible ? <MdCancel/> : <AiOutlineMenuFold/>}
                </button>
            </div>

            <nav
                className={`fixed top-0 h-full w-3/5 bg-[#061D31] transform transition-transform duration-500 ease-in-out ${
                    isPanelVisible ? "translate-x-0 right-0" : "translate-x-full right-0"
                } lg:translate-x-0 lg:static lg:flex lg:items-center lg:w-auto ml-auto`}
            >
                <ul className="menuList flex flex-col lg:flex-row gap-4 lg:gap-6 list-none text-center lg:text-left mt-20 lg:mt-0">
                    {[
                        {name: "Strona Głowna", path: "stronaGlowna"},
                        {name: "Wiadomosci", path: "wiadomosci"},
                        {name: "Poslowie", path: "poslowie"},
                        {name: "Partie", path: "partie"},
                        {name: "Forum", path: "forum"},
                        {name: "Głosowania", path: "glosowania"},
                        {name: "Ankiety", path: "ankiety"},
                        {name: "Transmisje", path: "transmisje"},
                    ].map((tab, index) => (
                        <li
                            key={index}
                            className={`relative group ${activeTab === tab.path ? "text-blue-300" : "text-white"}`}
                        >
                            <Link
                                to={`/${tab.path}`}
                                className={`font-bold text-lg font-cursive transition-colors duration-300 group-hover:text-blue-300 ${
                                    activeTab === tab.path ? "text-blue-300" : ""
                                }`}
                            >
                                {tab.name}
                            </Link>
                            <div
                                className={`absolute left-0 bottom-[-5px] h-[3px] rounded transition-all duration-300 bg-blue-400 ${
                                    activeTab === tab.path ? "w-full" : "w-0"
                                } group-hover:w-full`}
                            />
                        </li>
                    ))}
                </ul>

                <div
                    className="lg:hidden flex flex-col items-center gap-4 mt-4 absolute bottom-10 left-1/2 transform -translate-x-1/2">
                    {localStorage.getItem("refreshToken") ? (
                        <>
                            <img
                                src={userData?.profileImage || ''}
                                alt="Avatar"
                                className="w-16 h-16 rounded-full object-cover"
                            />
                            <p className="text-white font-bold text-lg">{userData?.firstName} {userData?.lastName}</p>
                            <button
                                onClick={handleLogout}
                                className="text-white font-bold text-lg border border-white px-4 py-2 rounded"
                            >
                                Wyloguj
                            </button>
                        </>
                    ) : (
                        <div
                            className="flex items-center gap-2"
                            onClick={() => navigate("/logowanie")}
                        >
                            <FaUserCircle className="text-white text-3xl"/>
                            <p className="text-white font-bold text-lg cursor-pointer">Login</p>
                        </div>
                    )}
                </div>
            </nav>

            <div className="hidden lg:flex items-center gap-4 ml-20 relative">
                {localStorage.getItem("refreshToken") ? (
                    <>
                        <img
                            src={userData?.profileImage || 'https://static.vecteezy.com/system/resources/thumbnails/009/292/244/small/default-avatar-icon-of-social-media-user-vector.jpg'}
                            alt="Avatar"
                            className="w-14 h-14 rounded-full object-cover"
                        />
                        <p
                            className="text-white font-bold text-lg cursor-pointer"
                            onClick={() => navigate(`/user-profile/${userData?.nickName}`)}
                        >
                            {userData?.firstName} {userData?.lastName}
                        </p>
                        <button
                            onClick={toggleMenu}
                            className="text-white text-xl focus:outline-none"
                        >
                            <BiDotsVerticalRounded/>
                        </button>
                        {isMenuOpen && (
                            <div
                                className={`absolute top-[7vh] right-0 mt-2 bg-[#061D31] shadow-lg rounded-lg w-48 overflow-hidden transition-all duration-300 ease-in-out transform scale-95 ${
                                    isMenuOpen ? "scale-100 opacity-100" : "opacity-0"
                                }`}
                            >
                                {userData?.roles?.some((role) => role.name === "ADMIN") && (
                                    <button
                                        onClick={() => {
                                            navigate(`/admin-news-panel`);
                                            setMenuOpen(false);
                                        }}
                                        className="w-full text-center px-4 py-2 text-white hover:bg-blue-500 hover:rounded transition-all duration-200"
                                    >
                                        Dodaj Artykuł
                                    </button>
                                )}
                                <button
                                    onClick={handleLogout}
                                    className="w-full text-center px-4 py-2 text-white hover:bg-red-500 hover:rounded transition-all duration-200"
                                >
                                    Wyloguj
                                </button>
                            </div>
                        )}
                    </>
                ) : (
                    <div
                        className="flex items-center gap-2"
                        onClick={() => navigate("/logowanie")}
                    >
                        <FaUserCircle className="text-white text-3xl"/>
                        <p className="text-white font-bold text-lg cursor-pointer">Login</p>
                    </div>
                )}
            </div>

        </header>
    );
}

export default Navbar;
