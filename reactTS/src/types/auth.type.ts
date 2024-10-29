export interface loginResponse {
    accessToken: string | null,
    refreshToken: string | null,
    isAuthenticated: boolean,
    user: user,
}

export interface user {
    id: number,
    name: string
}
