import React, {useState} from "react";
import {Link, useNavigate} from "react-router-dom";
import backgroundImage from '../assets/wwa.jpg';
import facebook from '../assets/facebook.png';
import google from '../assets/google.png';
import mail from '../assets/mail.png';
import logoEsejm from '../assets/logoEsejm.png';
import {useDispatch} from "react-redux";
import {loginAction} from "../redux/AuthService/Action.js";
import toast from "react-hot-toast";

function Login() {
    const [inputData, setInputData] = useState({email: "", password: ""});
    const navigate = useNavigate();
    const dispatch = useDispatch();

    const handleChange = (e) => {
        const { name, value } = e.target;
        setInputData((values) => ({ ...values, [name]: value }));
    };

    const loginWithGoogle = () => {
        localStorage.setItem("loginWithGoogle", "true");
        window.location.href = "http://localhost:8080/oauth2/authorization/google";
    };


    const handleSubmit = async (e) => {
        e.preventDefault();
        dispatch(loginAction(inputData))
            .then(() => {
                navigate("/stronaGlowna");
                toast.success("You have logged in successfully.");
            })
            .catch(() => {
                toast.error("Failed to login. Please try again.");
            })
        }

    return (
        <div
          className="h-screen w-full bg-cover bg-center"
          style={{ backgroundImage: `url(${backgroundImage})` }}
        >
          <div className="flex items-center justify-center h-full">
            <div className="bg-white bg-opacity-50 backdrop-blur-md rounded-xl p-8 shadow-lg max-w-4xl w-full flex">
              
              <div className="w-1/2 p-6 border-r border-gray-300">
                <h2 className="text-gray-800 text-xl font-semibold mb-4 mt-14 text-center">Zarejestruj się</h2>
                <div className="space-y-4">
                  <button className="w-full flex items-center justify-center bg-gray-100 p-3 rounded-lg hover:bg-gray-200 transition" onClick={loginWithGoogle}>
                    <img src={google} alt="Google" className="w-6 h-6 mr-3" />
                    <span className="text-gray-800">Google</span>
                  </button>
                  <button className="w-full flex items-center justify-center bg-gray-100 p-3 rounded-lg hover:bg-gray-200 transition">
                    <img src={facebook} alt="Facebook" className="w-6 h-6 mr-3" />
                    <span className="text-gray-800">Facebook</span>
                  </button>
                  <button className="w-full flex items-center justify-center bg-gray-100 p-3 rounded-lg hover:bg-gray-200 transition" onClick={() => navigate("/rejestracja")}>
                    <img src={mail} alt="Email" className="w-6 h-6 mr-3" />
                    <span className="text-gray-800">Email</span>
                  </button>
                </div>
              </div>
    
              <div className="w-1/2 p-6">

                <div className="flex justify-center mb-4">
                    <img src={logoEsejm} alt="Logo E-Sejm" className="h-12 w-auto" />
                </div>

                <h2 className="text-gray-800 text-xl font-semibold mb-4 text-center">Zaloguj się</h2>
                
                <form onSubmit={handleSubmit}>
                  
                  <div className="form-group">
                    <label className="text-gray-800">Email</label>
                    <input
                      type="email"
                      name="email"
                      className="w-full p-2 mt-1 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-700"
                      value={inputData.email} onChange={(e) => handleChange(e)}
                      required
                    />
                  </div>

                  <div className="form-group">
                    <label className="text-gray-800">Hasło</label>
                    <input
                      type="password"
                      name="password"
                      className="w-full p-2 mt-1 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-700"
                      value={inputData.password} onChange={(e) => handleChange(e)}
                      required
                    />
                  </div>

                  <div className="text-right">
                      <Link
                          to={"/forgot-password"}
                          className={"text-blue-700 text-sm hover:underline"}
                      >
                          Zapomniałeś hasła?
                      </Link>
                  </div>


                  <button
                    type="submit"
                    className="w-full bg-blue-700 text-white py-2 rounded-lg mt-4 hover:bg-blue-600 transition-colors"
                    onSubmit={handleSubmit}
                  >
                      Zaloguj się
                  </button>

                </form>
              </div>
            </div>
          </div>
        </div>
      );
}

export { Login };