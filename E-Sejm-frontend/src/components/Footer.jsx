import React from "react";
import { FaFacebook, FaInstagram } from "react-icons/fa";
import logo from "../assets/logoEsejm.png";
import { FaXTwitter } from "react-icons/fa6";

function Footer() {
    return (
        <footer className="bg-[#061D31] py-4 text-white">
            <div className="container mx-auto flex flex-col sm:flex-row justify-between items-center px-4">
                <div className="flex space-x-4 mb-4 lg:mb-0">
                    <a href="#" aria-label="Facebook" className="text-white hover:text-gray-300">
                        <FaFacebook size={24}/>
                    </a>
                    <a href="#" aria-label="Instagram" className="text-white hover:text-gray-300">
                        <FaInstagram size={24}/>
                    </a>
                    <a href="#" aria-label="X (formerly Twitter)" className="text-white hover:text-gray-300">
                        <FaXTwitter size={24}/>
                    </a>
                </div>

                <div className="flex flex-col items-center mb-4 lg:mb-0">
                    <img src={logo} alt="E-Sejm Logo" className="h-12 mb-2"/>
                    <p className="text-center text-sm">
                        E-Sejm© 2024 - Wszelkie prawa zastrzeżone
                    </p>
                </div>

                <div className="text-center lg:text-right space-y-2 lg:space-y-0 lg:space-x-4">
                    <a href="#" className="text-white text-sm hover:text-gray-300">
                        Regulamin
                    </a>
                    <a href="#" className="text-white text-sm hover:text-gray-300">
                        Polityka prywatności
                    </a>
                </div>
            </div>
        </footer>
    );
}

export default Footer;
