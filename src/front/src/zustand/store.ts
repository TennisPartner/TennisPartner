import { create } from "zustand";

interface stateType {
  hasClub: boolean;
  setHasClub: () => void;
}

const useUserDataStore = create((set) => ({
  hasClub: false,
  setHasClub: () => set((state: stateType) => ({ hasClub: !state.hasClub })),
}));

export default useUserDataStore;
