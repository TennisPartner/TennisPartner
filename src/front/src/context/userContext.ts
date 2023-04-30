// user state context
import { createContext } from "react";

// when user login or logout, we will update this context
export const userContext = createContext<any>(null);
